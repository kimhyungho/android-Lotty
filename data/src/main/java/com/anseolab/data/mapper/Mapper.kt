package com.anseolab.data.mapper

interface Mapper<Data, Domain> {
    fun mapToDomain(from: Data): Domain
}