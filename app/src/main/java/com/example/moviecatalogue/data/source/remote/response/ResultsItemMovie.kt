package com.example.moviecatalogue.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ResultsItemMovie(

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: Date? = null,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null,

    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int>? = null

) : Parcelable
