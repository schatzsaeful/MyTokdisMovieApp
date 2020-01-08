package com.example.mytokdismovieapp.ui.favorite.movie.movie_now_playing

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mytokdismovieapp.data.source.local.FavoriteRepository
import com.example.mytokdismovieapp.data.source.local.entity.MovieNowPlayingEntity
import com.example.mytokdismovieapp.data.source.remote.RemoteRepository
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre

class FavoriteMovieNowPlayingViewModel(application: Application?) : ViewModel() {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    val getAllMovieNowPlaying: LiveData<PagedList<MovieNowPlayingEntity>>
        get() = favoriteRepository.getAllMovieNowPlaying?.let {
            LivePagedListBuilder<Int, MovieNowPlayingEntity>(
                it, 20).build()
        } as LiveData<PagedList<MovieNowPlayingEntity>>

    fun getAllGenre() {
        if (resultItemGenre != null) {
            return
        }

        val remoteRepository = RemoteRepository.instance
        if (remoteRepository != null) {
            this.resultItemGenre = remoteRepository.getAllGenreMovieAsLiveData()
        }
    }

}