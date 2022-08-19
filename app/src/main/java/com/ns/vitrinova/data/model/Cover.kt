package com.ns.vitrinova.data.model

data class Cover(
    val height: Int,
    val medium: CoverMedium,
    val thumbnail: CoverThumbnail,
    val url: String,
    val width: Int
)