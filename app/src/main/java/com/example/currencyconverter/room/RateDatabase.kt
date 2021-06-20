package com.example.currencyconverter.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverter.RateItem

@Database(entities = [RateItem::class], version = 1)
abstract class RateDatabase : RoomDatabase() {

    abstract val rateDao: RateDao

    companion object {
        @Volatile
        private var INSTANCE: RateDatabase? = null
        fun getInstance(context: Context): RateDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RateDatabase::class.java,
                        "rate_database"
                    ).build()
                }
                return instance
            }
        }
    }

}