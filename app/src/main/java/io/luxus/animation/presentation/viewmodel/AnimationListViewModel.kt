package io.luxus.animation.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import io.luxus.animation.data.source.remote.model.DiscoverResult
import io.luxus.animation.domain.model.AnimationModel
import io.luxus.animation.domain.usecase.GetAnimationUseCase
import io.luxus.animation.presentation.view.adapter.RecyclerViewAdapter
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.annotations.NonNls
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class AnimationListViewModel @Inject constructor(
    private val getAnimationUseCase: GetAnimationUseCase
) : ViewModel() {

    private val TAG = AnimationListViewModel::class.java.simpleName

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val pageSize = 20

    private val animationList: ArrayList<AnimationModel> = ArrayList()

    private val numberPageHashMap = ConcurrentHashMap<Int, Int>()
    private val pagePositionHashMap = ConcurrentHashMap<Int, Int>()

    private var listListener: RecyclerViewAdapter.Listener? = null


    private var sortType: String = "rank"

    private var tPage: Int = 0
    private var bPage: Int = 1
    private var offset: Int = 0

    private var isLoading: Boolean = false


    private val nowPage = ObservableInt(1)
    private val maxPage = ObservableInt(-1)
    private val contentRange = ObservableInt(-1)

    fun init(listListener: RecyclerViewAdapter.Listener) {
        this.listListener = listListener
    }

    private fun setContentRange(length: Int) {
        contentRange.set(length)
        maxPage.set(contentRange.get() / pageSize + if (contentRange.get() % pageSize != 0) 1 else 0)
        Log.d(TAG, "cr:" + contentRange.get() + ", mp:" + maxPage.get())
    }


    private fun loadPage(page: Int): Single<DiscoverResult> {
        return getAnimationUseCase.getDiscover(sortType, page * pageSize, pageSize)
    }

    private fun loadPageSeparated(loadPage: Int, loadPageCallback: LoadPageCallback) {
        isLoading = true
        loadPage(loadPage)
                .subscribe(object : SingleObserver<DiscoverResult> {
                    override fun onSubscribe(@NonNls d: Disposable) {
                        disposable.add(d)
                    }

                    override fun onSuccess(result: DiscoverResult) {

                        setContentRange(result.count)

                        val newAnimationList: ArrayList<AnimationModel> = ArrayList()
                        result.results.forEach {
                            val animationModel = AnimationModel(
                                    it.id, it.img, it.name
                            )
                            newAnimationList.add(animationModel)
                            numberPageHashMap
                        }

                        loadPageCallback.call(newAnimationList)


                        isLoading = false
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "Failed to load animation List $loadPage")
                        e.printStackTrace()

                        isLoading = false
                    }
                })
    }

    fun loadTopPage() {
        if (isLoading || tPage < 1) return

        val displayPage = tPage--
        val loadPage = tPage

        loadPageSeparated(loadPage, object : LoadPageCallback {
            override fun call(animationModels: ArrayList<AnimationModel>) {
                val size: Int = animationModels.size
                synchronized(pagePositionHashMap) {
                    pagePositionHashMap[displayPage] = 0;
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
                }

                listListener?.onRangeInserted(offset, size);
            }
        })
    }

    fun loadBottomPage() {
        if (isLoading || maxPage.get() != -1 && bPage > maxPage.get()) return

        val loadPage = bPage - 1
        val displayPage = bPage++

        loadPageSeparated(loadPage, object : LoadPageCallback {
            override fun call(animationModels: ArrayList<AnimationModel>) {
                val listSize: Int = animationList.size
                val size: Int = animationModels.size

                synchronized(pagePositionHashMap) {
                    pagePositionHashMap[displayPage] = listSize
                    animationList.addAll(animationModels)
                }

                listListener?.onRangeInserted(listSize, size)
            }
        })
    }

    fun getAnimationList(): ArrayList<AnimationModel> = animationList

    interface LoadPageCallback {
        fun call(animationModels: ArrayList<AnimationModel>)
    }

}