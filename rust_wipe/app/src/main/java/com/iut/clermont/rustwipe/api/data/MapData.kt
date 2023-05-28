package com.iut.clermont.rustwipe.api.data

data class Monument(
    val biome: String,
    val monument: String,
    val prefab: String,
    val x: Int,
    val y: Int
)

data class MapData(
    val barren: Boolean,
    val created: String,
    val customMapConfig: Any?,
    val desertBiome: Double,
    val forestBiome: Double,
    val id: String,
    val imageIconUrl: String,
    val imageUrl: String,
    val mapDownloadUrl: Any?,
    val monuments: List<Monument>,
    val seed: Int,
    val size: Int,
    val snowBiome: Double,
    val staging: Boolean,
    val thumbnailUrl: String,
    val tundraBiome: Double,
    val url: String
)
