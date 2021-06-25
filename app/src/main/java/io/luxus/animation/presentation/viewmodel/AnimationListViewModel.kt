package io.luxus.animation.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.luxus.animation.data.source.remote.model.discover.DiscoverResult
import io.luxus.animation.domain.model.AnimationModel
import io.luxus.animation.domain.usecase.AnimationUseCase
import io.luxus.animation.presentation.view.adapter.RecyclerViewAdapter
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.NonNls
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class AnimationListViewModel @Inject constructor(
    private val animationUseCase: AnimationUseCase,
) : ViewModel() {

    companion object {
        private val TAG = AnimationListViewModel::class.java.simpleName
    }

    private val pageSize = 20
    val prefetchPage: Int = 2

    private val animationList = ArrayList<AnimationModel>()

    private val numberPageHashMap = ConcurrentHashMap<Int, Int>()
    private val pagePositionHashMap = ConcurrentHashMap<Int, Int>()

    private lateinit var listListener: RecyclerViewAdapter.Listener


    private var sortType: String = "rank"

    private var tPage: Int = 0
    private var bPage: Int = 1
    private var offset: Int = 0

    private var isLoading = false
    private var notDid = true

    private val nowPage = ObservableInt(1)
    private val maxPage = ObservableInt(-1)
    private val contentRange = ObservableInt(-1)
    private val loadStatus = MutableLiveData(LoadStatus.LOADING)

    enum class LoadStatus {
       FAILED, LOADING, SUCCEED
    }

    //private val initialLoadSucceed = MutableLiveData<Boolean>()
    //private var initiallyNotLoaded = true



    fun init(listListener: RecyclerViewAdapter.Listener) {
        this.listListener = listListener
    }

    fun loadFirst() {
        if (notDid) {
            notDid = false
            loadBottomPage()
        }
    }

    fun changePage(page: Int) {
        val maxPage = maxPage.get()
        val contentRange = contentRange.get()

        removeAll()
        setPage(page)
        this.maxPage.set(maxPage)
        this.contentRange.set(contentRange)
        this.loadBottomPage()
    }

    private fun removeAll() {
        val count = animationList.size
        listListener.onRangeRemovedSync(0, count)

        numberPageHashMap.clear()
        pagePositionHashMap.clear()
        animationList.clear()
        setPage(1)

        nowPage.set(1)
        maxPage.set(-1)
        contentRange.set(-1)
    }

    private fun setPage(page: Int) {
        bPage = page
        tPage = page - 1
    }

    private fun setContentRange(length: Int) {
        contentRange.set(length)
        maxPage.set(contentRange.get() / pageSize + if (contentRange.get() % pageSize != 0) 1 else 0)
        Log.d(TAG, "cr:" + contentRange.get() + ", mp:" + maxPage.get())
    }


    private fun loadPage(page: Int): DiscoverResult {
        return animationUseCase.getDiscover(sortType, pageSize, page * pageSize)
    }

    private fun loadPageSeparated(displayPage: Int, loadPageCallback: LoadPageCallback) {
        isLoading = true

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    loadPage(displayPage - 1)
                }
                if (loadStatus.value != LoadStatus.SUCCEED) loadStatus.value = LoadStatus.SUCCEED

                setContentRange(result.count)

                val newAnimationList: ArrayList<AnimationModel> = ArrayList()
                result.results.forEach {
                    val animationModel = AnimationModel(
                        it.id, it.img, it.name
                    )
                    newAnimationList.add(animationModel)
                    numberPageHashMap[it.id] = displayPage
                }
                loadPageCallback.call(newAnimationList)

            } catch (t: Throwable) {
                Log.e(TAG, "Failed to load animation List $displayPage", t)

                loadStatus.value = LoadStatus.FAILED
            } finally {
                isLoading = false
            }
        }
    }

    fun loadTopPage() {
        if (isLoading || tPage < 1) return

        val displayPage = tPage--
        //val loadPage = tPage

        loadPageSeparated(displayPage, object : LoadPageCallback {
            override fun call(animationModels: ArrayList<AnimationModel>) {
                val size: Int = animationModels.size
                //synchronized(pagePositionHashMap) {
                    pagePositionHashMap[displayPage] = 0
                    val to: Int = bPage
                    for (i in (displayPage + 1) until to) {
                        val position: Int? = pagePositionHashMap[i]

                        if (position != null) {
                            pagePositionHashMap[i] = position + size
                        } else {
                            Log.e(TAG, "position is NULL:$i")
                        }
                    }
                    animationList.addAll(offset, animationModels)
                //}

                listListener.onRangeInsertedSync(offset, size)
            }
        })
    }

    fun loadBottomPage() {
        if (isLoading || maxPage.get() != -1 && bPage > maxPage.get()) return

        //val loadPage = bPage - 1
        val displayPage = bPage++

        loadPageSeparated(displayPage, object : LoadPageCallback {
            override fun call(animationModels: ArrayList<AnimationModel>) {
                val listSize: Int = animationList.size
                val size: Int = animationModels.size

                synchronized(pagePositionHashMap) {
                    pagePositionHashMap[displayPage] = listSize
                    animationList.addAll(animationModels)
                }

                listListener.onRangeInsertedSync(listSize, size)
            }
        })
    }

    fun getAnimationList(): ArrayList<AnimationModel> = animationList

    fun getNumberPageHashMap(): ConcurrentHashMap<Int, Int> = numberPageHashMap

    fun getPagePositionHashMap(): ConcurrentHashMap<Int, Int> = pagePositionHashMap

    fun getNowPage(): ObservableInt = nowPage
    fun getMaxPage(): ObservableInt = maxPage
    fun getTPage(): Int = tPage
    fun getBPage(): Int = bPage

    fun getLoadStatus(): LiveData<LoadStatus> = loadStatus


    interface LoadPageCallback {
        fun call(animationModels: ArrayList<AnimationModel>)
    }


}