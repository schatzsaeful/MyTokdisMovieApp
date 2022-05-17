package com.example.moviecatalogue.data.source.remote.api

import com.example.moviecatalogue.data.source.remote.response.ResponseGenre
import com.example.moviecatalogue.data.source.remote.response.ResponseMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BaseApiService {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Call<ResponseMovie?>?

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Call<ResponseMovie?>?

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Call<ResponseMovie?>?

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Call<ResponseMovie?>?

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovie(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): Call<ResponseMovie?>?

    @GET("genre/movie/list")
    fun getGenre(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): Call<ResponseGenre?>?

}