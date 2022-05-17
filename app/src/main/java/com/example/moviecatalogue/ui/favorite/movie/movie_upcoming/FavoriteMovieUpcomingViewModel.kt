package com.example.moviecatalogue.ui.favorite.movie.movie_upcoming

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviecatalogue.data.source.local.FavoriteRepository
import com.example.moviecatalogue.data.source.local.entity.MovieUpcomingEntity
import com.example.moviecatalogue.data.source.remote.RemoteRepository
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre

class FavoriteMovieUpcomingViewModel(application: Application?) : ViewModel() {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    val getAllMovieUpcoming: LiveData<PagedList<MovieUpcomingEntity>>
        get() = favoriteRepository.getAllMovieUpcoming?.let {
            LivePagedListBuilder<Int, MovieUpcomingEntity>(
                it, 20).build()
        } as LiveData<PagedList<MovieUpcomingEntity>>

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