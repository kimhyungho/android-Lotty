package com.anseolab.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anseolab.local.model.dao.LotteryDao
import com.anseolab.local.model.entity.LotteryEntity
import com.google.gson.Gson

@Database(entities = [LotteryEntity::class], version = 1)
abstract class LotteryDataBase : RoomDatabase() {
    abstract val lotteryDao: LotteryDao

    companion object {
        @Volatile
        private var INSTANCE: LotteryDataBase? = null

        fun getInstance(applicationContext: Context, gson: Gson): LotteryDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(applicationContext, gson).also { INSTANCE = it }
            }

        private fun buildDatabase(applicationContext: Context, gson: Gson): LotteryDataBase =
            Room.databaseBuilder(
                applicationContext,
                LotteryDataBase::class.java,
                "lotteries"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}