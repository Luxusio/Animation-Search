package io.luxus.animation.data.source.remote

import io.luxus.animation.data.source.remote.model.discover.DiscoverResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("api/search/v1/discover/")
    fun getAnimationListFromDiscover(
        @Query("sort") sortType: String,
        @Query("viewable") viewable: Boolean,
        @Query("size") size: Int,
        @Query("offset") offset: Int
    ): Single<DiscoverResult>

}