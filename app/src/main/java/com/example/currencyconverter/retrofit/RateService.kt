package com.example.currencyconverter.retrofit

import com.example.currencyconverter.RateItem
import retrofit2.Response
import retrofit2.http.*

interface RateService {
    @GET("/rates.php")
    suspend fun getRates(@Query("base") code: String): Response<ArrayList<RateItem>>

//    @GET("/albums")
//    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<Albums>
//    @GET("/albums/{id}")
//    suspend fun getAlbum(@Path(value = "id") albumId: Int): Response<AlbumsItem>
//
//    @POST("/albums")
//    suspend fun uploadAlbum(@Body album: AlbumsItem): Response<AlbumsItem>


}