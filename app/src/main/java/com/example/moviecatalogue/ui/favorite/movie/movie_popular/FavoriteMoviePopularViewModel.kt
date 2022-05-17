package com.example.moviecatalogue.ui.favorite.movie.movie_popular

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviecatalogue.data.source.local.FavoriteRepository
import com.example.moviecatalogue.data.source.local.entity.MoviePopularEntity
import com.example.moviecatalogue.data.source.remote.RemoteRepository
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre

class FavoriteMoviePopularViewModel(application: Application?) : ViewModel() {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    val getAllMoviePopular: LiveData<PagedList<MoviePopularEntity>>
        get() = favoriteRepository.getAllMoviePopular?.let {
            LivePagedListBuilder<Int, MoviePopularEntity>(
                it, 20).build()
        } as LiveData<PagedList<MoviePopularEntity>>

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