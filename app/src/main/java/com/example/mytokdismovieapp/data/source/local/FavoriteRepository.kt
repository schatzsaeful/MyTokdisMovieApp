package com.example.mytokdismovieapp.data.source.local

import android.app.Application
import androidx.paging.DataSource
import com.example.mytokdismovieapp.data.source.local.entity.MovieNowPlayingEntity
import com.example.mytokdismovieapp.data.source.local.entity.MoviePopularEntity
import com.example.mytokdismovieapp.data.source.local.entity.MovieTopRatedEntity
import com.example.mytokdismovieapp.data.source.local.entity.MovieUpcomingEntity
import com.example.mytokdismovieapp.data.source.local.room.MovieDao
import com.example.mytokdismovieapp.data.source.local.room.MovieDatabase.Companion.getFavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application?) {
    private val mCataloguesDao: MovieDao?
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    // MOVIE POPULAR

    val getAllMoviePopular: DataSource.Factory<Int?, MoviePopularEntity?>?
        get() = mCataloguesDao?.moviePopular

    fun insertMoviePopular(movie: MoviePopularEntity?) {
        executorService.execute {
            mCataloguesDao?.insertMoviePopular(movie) }
    }

    fun deleteMoviePopular(movie: MoviePopularEntity?) {
        executorService.execute {
            mCataloguesDao?.deleteMoviePopular(movie) }
    }

    // MOVIE TOP RATED

    val getAllMovieTopRated: DataSource.Factory<Int?, MovieTopRatedEntity?>?
        get() = mCataloguesDao?.movieTopRated

    fun insertMovieTopRated(movie: MovieTopRatedEntity?) {
        executorService.execute {
            mCataloguesDao?.insertMovieTopRated(movie) }
    }

    fun deleteMovieTopRated(movie: MovieTopRatedEntity?) {
        executorService.execute {
            mCataloguesDao?.deleteMovieTopRated(movie) }
    }

    // MOVIE UPCOMING

    val getAllMovieUpcoming: DataSource.Factory<Int?, MovieUpcomingEntity?>?
        get() = mCataloguesDao?.movieUpcoming

    fun insertMovieUpcoming(movie: MovieUpcomingEntity?) {
        executorService.execute {
            mCataloguesDao?.insertMovieUpcoming(movie) }
    }

    fun deleteMovieUpcoming(movie: MovieUpcomingEntity?) {
        executorService.execute {
            mCataloguesDao?.deleteMovieUpcoming(movie) }
    }

    // MOVIE NOW PLAYING

    val getAllMovieNowPlaying: DataSource.Factory<Int?, MovieNowPlayingEntity?>?
        get() = mCataloguesDao?.movieNowPlaying

    fun insertMovieNowPlaying(movie: MovieNowPlayingEntity?) {
        executorService.execute {
            mCataloguesDao?.insertMovieNowPlaying(movie) }
    }

    fun deleteMovieNowPlaying(movie: MovieNowPlayingEntity?) {
        executorService.execute {
            mCataloguesDao?.deleteMovieNowPlaying(movie) }
    }

    init {
        val db = application?.let { getFavoriteDatabase(it) }
        mCataloguesDao = db?.movieDao()
    }
}