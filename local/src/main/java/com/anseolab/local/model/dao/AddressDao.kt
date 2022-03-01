package com.anseolab.local.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anseolab.local.model.entity.AddressEntity
import com.anseolab.local.model.entity.DrwtNoEntity
import io.reactivex.rxjava3.core.Flowable

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(vararg address: AddressEntity)

    @Query("SELECT * FROM Address ORDER BY createdAt DESC")
    fun getAll(): Flowable<List<AddressEntity>>

    @Query("DELETE FROM Address WHERE address= :address")
    fun remove(address: String)

    @Query("DELETE FROM Address")
    fun clear()
}