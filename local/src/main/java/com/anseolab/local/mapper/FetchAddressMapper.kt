package com.anseolab.local.mapper

import com.anseolab.local.model.entity.AddressEntity

object FetchAddressMapper: Mapper<List<AddressEntity>, List<String>> {
    override fun mapToData(from: List<AddressEntity>): List<String> {
        return from.map {
            it.address
        }
    }
}