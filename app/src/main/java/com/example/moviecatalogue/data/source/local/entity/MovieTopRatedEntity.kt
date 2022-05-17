package com.example.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tMovieTopRated")
data class MovieTopRatedEntity(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movieMovieTopRatedId")
    var movieMovieTopRatedId: Int? = null,

    @ColumnInfo(name = "movieMovieTopRatedPoster")
    var movieMovieTopRatedPoster: String? = null,

    @ColumnInfo(name = "movieMovieTopRatedTitle")
    var movieMovieTopRatedTitle: String? = null,

    @ColumnInfo(name = "movieMovieTopRatedRelease")
    var movieMovieTopRatedRelease: String? = null,

    @ColumnInfo(name = "movieMovieTopRatedVoteAverage")
    var movieMovieTopRatedVoteAverage: Double? = null,

    @ColumnInfo(name = "movieMovieTopRatedVoteCount")
    var movieMovieTopRatedVoteCount: Int? = null,

    @ColumnInfo(name = "movieMovieTopRatedOverview")
    var movieMovieTopRatedOverview: String? = null,

    @ColumnInfo(name = "movieMovieTopRatedGenre")
    var movieMovieTopRatedGenre: String? = null

) : Parcelable