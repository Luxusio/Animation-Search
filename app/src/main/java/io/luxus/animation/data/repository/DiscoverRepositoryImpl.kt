package io.luxus.animation.data.repository

import io.luxus.animation.data.source.remote.RetrofitService
import io.luxus.animation.data.source.remote.model.discover.DiscoverResult
import io.luxus.animation.domain.repository.DiscoverRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverRepositoryImpl @Inject constructor(private val retrofitService: RetrofitService) : DiscoverRepository {

    override fun getAnimationListResult(sortType: String, size: Int, offset: Int): DiscoverResult =
        retrofitService.getAnimationListFromDiscover(sortType, true, size, offset).execute().body()!!


}