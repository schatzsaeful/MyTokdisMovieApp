package com.example.moviecatalogue.ui.movie.movie_popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.source.remote.RemoteRepository
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie

class MoviePopularViewModel: ViewModel() {

    var resultItemMovie: LiveData<List<ResultsItemMovie>>? = null
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    fun getAllPopularMovie(page: Int) {

        val remoteRepository = RemoteRepository.instance
        if (remoteRepository != null) {
            this.resultItemMovie = remoteRepository.getAllPopularMovieAsLiveData(page)
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