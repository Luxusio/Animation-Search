package io.luxus.animation.data.source.remote.model

data class DiscoverResult(
    val count: Int,
    val next: String,
    val results: List<DiscoverAnimation>
)