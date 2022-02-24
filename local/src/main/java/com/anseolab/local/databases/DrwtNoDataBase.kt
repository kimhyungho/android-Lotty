package com.anseolab.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anseolab.local.model.dao.DrwtNoDao
import com.anseolab.local.model.dao.LotteryDao
import com.anseolab.local.model.entity.DrwtNoEntity
import com.google.gson.Gson

@Database(entities = [DrwtNoEntity::class], version = 1)
abstract class DrwtNoDataBase: RoomDatabase() {
    abstract val drwtNoDao: DrwtNoDao

    companion object {
        @Volatile
        private var INSTANCE: DrwtNoDataBase? = null

        fun getInstance(applicationContext: Context, gson: Gson): DrwtNoDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(applicationContext, gson).also { INSTANCE = it }
            }

        private fun buildDatabase(applicationContext: Context, gson: Gson): DrwtNoDataBase =
            Room.databaseBuilder(
                applicationContext,
                DrwtNoDataBase::class.java,
                "drwtNos"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}