package io.luxus.animation.data.source.remote.model.discover

data class DiscoverResult(
    val count: Int,
    val next: String,
    val results: List<DiscoverAnimation>
)