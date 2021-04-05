package io.luxus.animation.data.repository

import io.luxus.animation.data.source.remote.RetrofitService
import io.luxus.animation.data.source.remote.model.discover.DiscoverResult
import io.luxus.animation.domain.repository.DiscoverRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverRepositoryImpl @Inject constructor(private val retrofitService: RetrofitService) : DiscoverRepository {

    override fun getAnimationListResult(sortType: String, size: Int, offset: Int): Single<DiscoverResult> {
        return retrofitService.getAnimationListFromDiscover(sortType, true, size, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}