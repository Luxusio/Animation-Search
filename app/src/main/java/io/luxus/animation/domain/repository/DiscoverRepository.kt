package io.luxus.animation.domain.repository

import io.luxus.animation.data.source.remote.model.DiscoverResult
import io.luxus.animation.domain.model.AnimationModel
import io.reactivex.Single
import retrofit2.http.Query

interface DiscoverRepository {

    fun getAnimationListResult(sortType: String, size: Int, offset: Int): Single<DiscoverResult>

}