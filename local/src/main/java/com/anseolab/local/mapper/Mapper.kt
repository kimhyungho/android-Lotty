package com.anseolab.local.mapper

interface Mapper<E, D> {
    fun mapToData(from: E): D
}