package com.anseolab.remote.mapper

interface Mapper<R, D> {
    fun mapToData(from: R): D
}