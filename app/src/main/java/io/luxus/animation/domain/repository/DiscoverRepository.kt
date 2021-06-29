package io.luxus.animation.domain.repository

import io.luxus.animation.data.source.remote.model.discover.DiscoverResult

interface DiscoverRepository {

    fun getAnimationListResult(sortType: String, size: Int, offset: Int): DiscoverResult

}