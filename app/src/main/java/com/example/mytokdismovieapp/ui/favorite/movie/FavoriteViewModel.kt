package com.example.mytokdismovieapp.ui.favorite.movie

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mytokdismovieapp.data.source.local.FavoriteRepository
import com.example.mytokdismovieapp.data.source.local.entity.MovieNowPlayingEntity
import com.example.mytokdismovieapp.data.source.local.entity.MoviePopularEntity
import com.example.mytokdismovieapp.data.source.local.entity.MovieTopRatedEntity
import com.example.mytokdismovieapp.data.source.local.entity.MovieUpcomingEntity

class FavoriteViewModel(application: Application?) : ViewModel() {
    private val favoriteRepository: FavoriteRepository =
        FavoriteRepository(
            application
        )

    // MOVIE POPULAR

    fun insertMoviePopular(movie: MoviePopularEntity?) {
        favoriteRepository.insertMoviePopular(movie)
    }

    fun deleteMoviePopular(movie: MoviePopularEntity?) {
        favoriteRepository.deleteMoviePopular(movie)
    }

    // MOVIE TOP RATED

    fun insertMovieTopRated(movie: MovieTopRatedEntity?) {
        favoriteRepository.insertMovieTopRated(movie)
    }

    fun deleteMovieTopRated(movie: MovieTopRatedEntity?) {
        favoriteRepository.deleteMovieTopRated(movie)
    }

    // MOVIE UPCOMING

    fun insertMovieUpcoming(movie: MovieUpcomingEntity?) {
        favoriteRepository.insertMovieUpcoming(movie)
    }

    fun deleteMovieUpcoming(movie: MovieUpcomingEntity?) {
        favoriteRepository.deleteMovieUpcoming(movie)
    }

    // MOVIE NOW PLAYING

    fun insertMovieNowPlaying(movie: MovieNowPlayingEntity?) {
        favoriteRepository.insertMovieNowPlaying(movie)
    }

    fun deleteMovieNowPlaying(movie: MovieNowPlayingEntity?) {
        favoriteRepository.deleteMovieNowPlaying(movie)
    }

}