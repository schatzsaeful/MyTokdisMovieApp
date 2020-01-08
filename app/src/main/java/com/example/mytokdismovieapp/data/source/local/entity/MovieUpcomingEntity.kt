package com.example.mytokdismovieapp.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tMovieUpcoming")
data class MovieUpcomingEntity(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movieMovieUpcomingId")
    var movieMovieUpcomingId: Int? = null,

    @ColumnInfo(name = "movieMovieUpcomingPoster")
    var movieMovieUpcomingPoster: String? = null,

    @ColumnInfo(name = "movieMovieUpcomingTitle")
    var movieMovieUpcomingTitle: String? = null,

    @ColumnInfo(name = "movieMovieUpcomingRelease")
    var movieMovieUpcomingRelease: String? = null,

    @ColumnInfo(name = "movieMovieUpcomingVoteAverage")
    var movieMovieUpcomingVoteAverage: Double? = null,

    @ColumnInfo(name = "movieMovieUpcomingVoteCount")
    var movieMovieUpcomingVoteCount: Int? = null,

    @ColumnInfo(name = "movieMovieUpcomingOverview")
    var movieMovieUpcomingOverview: String? = null,

    @ColumnInfo(name = "movieMovieUpcomingGenre")
    var movieMovieUpcomingGenre: String? = null

) : Parcelable