package com.example.mytokdismovieapp.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tMoviePopular")
data class MoviePopularEntity(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movieMoviePopularId")
    var movieMoviePopularId: Int? = null,

    @ColumnInfo(name = "movieMoviePopularPoster")
    var movieMoviePopularPoster: String? = null,

    @ColumnInfo(name = "movieMoviePopularTitle")
    var movieMoviePopularTitle: String? = null,

    @ColumnInfo(name = "movieMoviePopularRelease")
    var movieMoviePopularRelease: String? = null,

    @ColumnInfo(name = "movieMoviePopularVoteAverage")
    var movieMoviePopularVoteAverage: Double? = null,

    @ColumnInfo(name = "movieMoviePopularVoteCount")
    var movieMoviePopularVoteCount: Int? = null,

    @ColumnInfo(name = "movieMoviePopularOverview")
    var movieMoviePopularOverview: String? = null,

    @ColumnInfo(name = "movieMoviePopularGenre")
    var movieMoviePopularGenre: String? = null

) : Parcelable