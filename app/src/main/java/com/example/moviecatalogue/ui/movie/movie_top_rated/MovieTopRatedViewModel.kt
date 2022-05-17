package com.example.moviecatalogue.ui.movie.movie_top_rated

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.source.remote.RemoteRepository
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie

class MovieTopRatedViewModel: ViewModel() {

    var resultItemMovie: LiveData<List<ResultsItemMovie>>? = null
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    fun getAllTopRatedMovie(page: Int) {
        if (resultItemMovie != null) {
            return
        }

        val remoteRepository = RemoteRepository.instance
        if (remoteRepository != null) {
            this.resultItemMovie = remoteRepository.getAllTopRatedMovieAsLiveData(page)
        }
    }

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