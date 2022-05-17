package com.example.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.moviecatalogue.data.source.local.entity.*

@Dao
interface MovieDao {

    // MOVIE POPULAR

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoviePopular(note: MoviePopularEntity?)

    @Delete
    fun deleteMoviePopular(note: MoviePopularEntity?)

    @get:Query("SELECT * from tMoviePopular ORDER BY movieMoviePopularId ASC")
    val moviePopular: DataSource.Factory<Int?, MoviePopularEntity?>?

    @Transaction
    @Query("SELECT * FROM tMoviePopular WHERE movieMoviePopularId = :movieId")
    fun getMoviePopularById(movieId: String): LiveData<MoviePopularEntity?>

    // MOVIE TOP RATED

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieTopRated(note: MovieTopRatedEntity?)

    @Delete
    fun deleteMovieTopRated(note: MovieTopRatedEntity?)

    @get:Query("SELECT * from tMovieTopRated ORDER BY movieMovieTopRatedId ASC")
    val movieTopRated: DataSource.Factory<Int?, MovieTopRatedEntity?>?

    @Transaction
    @Query("SELECT * FROM tMovieTopRated WHERE movieMovieTopRatedId = :movieId")
    fun getMovieTopRatedById(movieId: String): LiveData<MovieTopRatedEntity>

    // MOVIE UPCOMING

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieUpcoming(note: MovieUpcomingEntity?)

    @Delete
    fun deleteMovieUpcoming(note: MovieUpcomingEntity?)

    @get:Query("SELECT * from tMovieUpcoming ORDER BY movieMovieUpcomingId ASC")
    val movieUpcoming: DataSource.Factory<Int?, MovieUpcomingEntity?>?

    @Transaction
    @Query("SELECT * FROM tMovieUpcoming WHERE movieMovieUpcomingId = :movieId")
    fun getMovieUpcomingById(movieId: String): LiveData<MovieUpcomingEntity>

    // MOVIE NOW PLAYING

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieNowPlaying(note: MovieNowPlayingEntity?)

    @Delete
    fun deleteMovieNowPlaying(note: MovieNowPlayingEntity?)

    @get:Query("SELECT * from tMovieNowPlaying ORDER BY movieNowPlayingId ASC")
    val movieNowPlaying: DataSource.Factory<Int?, MovieNowPlayingEntity?>?

    @Transaction
    @Query("SELECT * FROM tMovieNowPlaying WHERE movieNowPlayingId = :movieId")
    fun getMovieNowPlayingById(movieId: String): LiveData<MovieNowPlayingEntity>

}