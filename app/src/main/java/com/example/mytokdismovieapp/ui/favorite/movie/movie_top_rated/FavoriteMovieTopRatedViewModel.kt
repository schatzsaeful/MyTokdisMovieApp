package com.example.mytokdismovieapp.ui.favorite.movie.movie_top_rated

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mytokdismovieapp.data.source.local.FavoriteRepository
import com.example.mytokdismovieapp.data.source.local.entity.MovieTopRatedEntity
import com.example.mytokdismovieapp.data.source.remote.RemoteRepository
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre

class FavoriteMovieTopRatedViewModel(application: Application?) : ViewModel() {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    val getAllMovieTopRated: LiveData<PagedList<MovieTopRatedEntity>>
        get() = favoriteRepository.getAllMovieTopRated?.let {
            LivePagedListBuilder<Int, MovieTopRatedEntity>(
                it, 20).build()
        } as LiveData<PagedList<MovieTopRatedEntity>>

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