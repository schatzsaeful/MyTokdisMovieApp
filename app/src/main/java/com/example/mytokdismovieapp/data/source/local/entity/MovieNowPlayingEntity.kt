package com.example.mytokdismovieapp.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tMovieNowPlaying")
data class MovieNowPlayingEntity(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movieNowPlayingId")
    var movieNowPlayingId: Int? = null,

    @ColumnInfo(name = "movieNowPlayingPoster")
    var movieNowPlayingPoster: String? = null,

    @ColumnInfo(name = "movieNowPlayingTitle")
    var movieNowPlayingTitle: String? = null,

    @ColumnInfo(name = "movieNowPlayingRelease")
    var movieNowPlayingRelease: String? = null,

    @ColumnInfo(name = "movieNowPlayingVoteAverage")
    var movieNowPlayingVoteAverage: Double? = null,

    @ColumnInfo(name = "movieNowPlayingVoteCount")
    var movieNowPlayingVoteCount: Int? = null,

    @ColumnInfo(name = "movieNowPlayingOverview")
    var movieNowPlayingOverview: String? = null,

    @ColumnInfo(name = "movieNowPlayingGenre")
    var movieNowPlayingGenre: String? = null

) : Parcelable