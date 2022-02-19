package com.anseolab.local.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anseolab.local.model.entity.LotteryEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface LotteryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(vararg lotteries: LotteryEntity) : Completable

    @Query("SELECT * FROM Lottery")
    fun getAll(): Flowable<List<LotteryEntity>>

    @Query("DELETE FROM Lottery WHERE drwNo= :drwNo")
    fun remove(drwNo: Long): Completable

    @Query("DELETE FROM Lottery")
    fun clear() : Completable

}