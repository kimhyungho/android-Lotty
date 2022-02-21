package com.anseolab.domain.model

data class Store(
    val id: Long,
    val name: String,
    val address: String,
    val roadAddress: String,
    val shortAddress: List<String>,
    val x: Double,
    val y: Double
)