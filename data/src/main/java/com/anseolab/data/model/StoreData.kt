package com.anseolab.data.model

data class StoreData(
    val id: Long,
    val name: String,
    val address: String,
    val roadAddress: String,
    val shortAddress: List<String>,
    val x: Double,
    val y: Double
)