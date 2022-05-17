package com.example.moviecatalogue.ui.movie.movie_upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.source.remote.RemoteRepository.Companion.instance
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie

class MovieUpcomingViewModel: ViewModel() {

    var resultItemMovie: LiveData<List<ResultsItemMovie>>? = null
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    fun getAllUpcomingMovie(page: Int) {
        if (resultItemMovie != null) {
            return
        }

        val remoteRepository = instance
        if (remoteRepository != null) {
            this.resultItemMovie = remoteRepository.getAllUpcomingMovieAsLiveData(page)
        }
    }

    fun getAllGenre() {
        if (resultItemGenre != null) {
            return
        }

        val remoteRepository = instance
        if (remoteRepository != null) {
            this.resultItemGenre = remoteRepository.getAllGenreMovieAsLiveData()
        }
    }

}