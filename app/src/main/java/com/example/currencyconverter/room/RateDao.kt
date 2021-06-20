package com.example.currencyconverter.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.currencyconverter.RateItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: ArrayList<RateItem>)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(list: ArrayList<RateItem>)
    @Query("SELECT * FROM rates")
    fun getAllRates(): LiveData<List<RateItem>>
}