package com.anseolab.local.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anseolab.local.model.entity.DrwtNoEntity
import com.anseolab.local.model.entity.LotteryEntity
import io.reactivex.rxjava3.core.Flowable

@Dao
interface DrwtNoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(vararg drwtNos: DrwtNoEntity)

    @Query("SELECT * FROM DrwtNo ORDER BY createdAt DESC")
    fun getAll(): Flowable<List<DrwtNoEntity>>

    @Query("DELETE FROM DrwtNo WHERE id= :id")
    fun remove(id: Int)

    @Query("DELETE FROM DrwtNo")
    fun clear()
}