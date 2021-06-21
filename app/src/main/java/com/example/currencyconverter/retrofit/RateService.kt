package com.example.currencyconverter.retrofit

import com.example.currencyconverter.RateItem
import retrofit2.Response
import retrofit2.http.*

interface RateService {
    @GET("/rates.php")
    suspend fun getRates(@Query("base") code: String): Response<ArrayList<RateItem>>

}