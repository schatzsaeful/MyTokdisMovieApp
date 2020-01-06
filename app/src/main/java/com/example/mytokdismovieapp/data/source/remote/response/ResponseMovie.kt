package com.example.mytokdismovieapp.data.source.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseMovie(

    @SerializedName("page")
    @Expose
    val page: Int? = null,

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null,

    @SerializedName("results")
    @Expose
    val results: List<ResultsItemMovie>? = null,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = null

)