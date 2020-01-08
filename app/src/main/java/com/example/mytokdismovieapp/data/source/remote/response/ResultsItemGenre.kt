package com.example.mytokdismovieapp.data.source.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResultsItemGenre(

	@SerializedName("name")
	@Expose
	val name: String? = null,

	@SerializedName("id")
	@Expose
	val id: Int? = null
)