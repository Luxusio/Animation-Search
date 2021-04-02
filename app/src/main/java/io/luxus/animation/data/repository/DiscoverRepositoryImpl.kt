package io.luxus.animation.data.repository

import io.luxus.animation.data.source.remote.RetrofitService
import io.luxus.animation.data.source.remote.model.DiscoverResult
import io.luxus.animation.domain.model.AnimationModel
import io.luxus.animation.domain.repository.DiscoverRepository
import io.reactivex.Single

class DiscoverRepositoryImpl(private val retrofitService: RetrofitService) : DiscoverRepository {

    override fun getAnimationListResult(sortType: String, size: Int, offset: Int): Single<DiscoverResult> {
        return retrofitService.getAnimationListFromDiscover(sortType, true, size, offset)
    }

}