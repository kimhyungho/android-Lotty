package com.anseolab.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anseolab.local.model.dao.AddressDao
import com.anseolab.local.model.entity.AddressEntity
import com.google.gson.Gson

@Database(entities = [AddressEntity::class], version = 1)
abstract class AddressDatabase: RoomDatabase() {
    abstract val addressDao: AddressDao

    companion object {
        @Volatile
        private var INSTANCE: AddressDatabase? = null

        fun getInstance(applicationContext: Context, gson: Gson): AddressDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(applicationContext, gson).also { INSTANCE = it }
            }

        private fun buildDatabase(applicationContext: Context, gson: Gson): AddressDatabase =
            Room.databaseBuilder(
                applicationContext,
                AddressDatabase::class.java,
                "addresses"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}