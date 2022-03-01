package com.anseolab.local.datasources

import com.anseolab.data.datasources.local.AddressLocalDataSource
import com.anseolab.local.databases.AddressDatabase
import com.anseolab.local.mapper.FetchAddressMapper
import com.anseolab.local.model.entity.AddressEntity
import io.reactivex.rxjava3.core.Flowable
import java.util.*
import javax.inject.Inject

class AddressLocalDataSourceImpl @Inject constructor(
    private val addressDatabase: AddressDatabase
): AddressLocalDataSource {
    override fun getAll(): Flowable<List<String>> {
        return addressDatabase.addressDao.getAll()
            .map(FetchAddressMapper::mapToData)
    }

    override fun set(value: List<String>) {
        throw UnsupportedOperationException()
    }

    override fun set(value: String) {
        val entity = AddressEntity(value, Date().time)
        return addressDatabase.addressDao.set(entity)
    }

    override fun remove(key: String) {
        return addressDatabase.addressDao.remove(key)
    }

    override fun clear() {
        return addressDatabase.addressDao.clear()
    }
}