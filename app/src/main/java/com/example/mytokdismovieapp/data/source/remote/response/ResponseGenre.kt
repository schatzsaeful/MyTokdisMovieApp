package com.example.mytokdismovieapp.data.source.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseGenre(

	@SerializedName("genres")
	@Expose
	val genres: List<ResultsItemGenre>? = null
)