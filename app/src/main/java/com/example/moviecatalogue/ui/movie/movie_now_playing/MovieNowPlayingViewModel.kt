package com.example.moviecatalogue.ui.movie.movie_now_playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.source.remote.RemoteRepository
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie

class MovieNowPlayingViewModel: ViewModel() {

    var resultItemMovie: LiveData<List<ResultsItemMovie>>? = null
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    fun getAllNowPlayingMovie(page: Int) {
        if (resultItemMovie != null) {
            return
        }

        val remoteRepository = RemoteRepository.instance
        if (remoteRepository != null) {
            this.resultItemMovie = remoteRepository.getAllNowPlayingMovieAsLiveData(page)
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