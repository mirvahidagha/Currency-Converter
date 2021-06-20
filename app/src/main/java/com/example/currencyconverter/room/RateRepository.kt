package com.example.currencyconverter.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.currencyconverter.RateItem
import com.example.currencyconverter.retrofit.RateService
import com.example.currencyconverter.retrofit.RetrofitInstance
import retrofit2.Response
import java.io.IOException

class RateRepository(private val context: Context) {
    val dao = RateDatabase.getInstance(context).rateDao

    val rateService = RetrofitInstance
        .getRetrofitInstance()
        .create(RateService::class.java)

    val rates = dao.getAllRates()


    fun getRatesFromServer():LiveData<Response<ArrayList<RateItem>>>?{
        try {return liveData {
            val response = rateService.getRates("AZN")
            emit(response)
        }
        }catch(ex: IOException) {
            return null
        }
    }


    suspend fun insertAll(list: ArrayList<RateItem>) {
        dao.insertAll(list)
    }

    suspend fun updateAll(list: ArrayList<RateItem>) {
        dao.updateAll(list)
    }


}