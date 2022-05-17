package com.example.moviecatalogue.ui.favorite.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalogue.ui.favorite.movie.FavoriteViewModel
import com.example.moviecatalogue.ui.favorite.movie.movie_now_playing.FavoriteMovieNowPlayingViewModel
import com.example.moviecatalogue.ui.favorite.movie.movie_popular.FavoriteMoviePopularViewModel
import com.example.moviecatalogue.ui.favorite.movie.movie_top_rated.FavoriteMovieTopRatedViewModel
import com.example.moviecatalogue.ui.favorite.movie.movie_upcoming.FavoriteMovieUpcomingViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteMoviePopularViewModel::class.java)) {
            return FavoriteMoviePopularViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteMovieTopRatedViewModel::class.java)) {
            return FavoriteMovieTopRatedViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteMovieUpcomingViewModel::class.java)) {
            return FavoriteMovieUpcomingViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteMovieNowPlayingViewModel::class.java)) {
            return FavoriteMovieNowPlayingViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
            }
            return INSTANCE
        }
    }

}