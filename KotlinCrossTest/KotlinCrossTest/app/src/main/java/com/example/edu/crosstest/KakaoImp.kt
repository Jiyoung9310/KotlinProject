package com.example.edu.crosstest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoImp {
    @Headers("Authorization: KakaoAK 2ddcb725149de71a27a9f8601ab16778")
    @GET("v2/search/image")
    fun imgSearch(@Query("query") query: String,
                  @Query("sort") sort: String,
                  @Query("page") page: Int,
                  @Query("size") size: Int): Call<Model>
}