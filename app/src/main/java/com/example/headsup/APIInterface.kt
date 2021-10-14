package com.example.headsup

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("/celebrities/")
    fun getCelebrities() : Call<List<CelebrityInfo.Info>>

    @POST("/celebrities/")
    fun addCelebrities(@Body itemData : EditCelebrity) : Call<EditCelebrity>

    @PUT("/celebrities/{id}")
    fun updateCelebrity(@Path("id") id: Int, @Body userData:CelebrityInfo.Info) : Call<CelebrityInfo.Info>

    @DELETE("/celebrities/{id}")
    fun deleteCelebrity(@Path("id") id: Int): Call<Void>
}