package com.anseolab.lotty.mapper

internal interface Mapper<D, T> {
    fun mapToView(from: D): T
}