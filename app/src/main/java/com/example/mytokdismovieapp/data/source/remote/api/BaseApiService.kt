package com.example.mytokdismovieapp.data.source.remote.api

import com.example.mytokdismovieapp.data.source.remote.response.ResponseMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface BaseApiService {

    @GET("movie/upcoming")
    fun getMovie(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int
    ): Call<ResponseMovie?>?

}