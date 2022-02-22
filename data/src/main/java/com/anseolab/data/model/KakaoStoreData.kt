package com.anseolab.data.model

data class KakaoStoreData (
    val id: Long,
    val addressName: String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: Double,
    val y: Double
)