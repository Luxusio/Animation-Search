package io.luxus.animation.domain.usecase

import io.luxus.animation.data.source.remote.model.DiscoverResult
import io.luxus.animation.domain.repository.DiscoverRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAnimationUseCase
    @Inject constructor(private val discoverRepository: DiscoverRepository) {

    fun getDiscover(sortType: String, size: Int, offset: Int): Single<DiscoverResult> {
        return discoverRepository.getAnimationListResult(sortType, size, offset)
    }



}