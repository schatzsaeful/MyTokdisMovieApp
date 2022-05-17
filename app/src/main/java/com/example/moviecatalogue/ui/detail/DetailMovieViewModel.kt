package com.example.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.source.remote.RemoteRepository
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie

class DetailMovieViewModel: ViewModel() {

    var resultItemSimilar: LiveData<List<ResultsItemMovie>>? = null
    var resultItemGenre: LiveData<List<ResultsItemGenre>>? = null

    fun getAllSimilarMovie(id: Int) {
        if (resultItemSimilar != null) {
            return
        }

        val remoteRepository = RemoteRepository.instance
        if (remoteRepository != null) {
            this.resultItemSimilar = remoteRepository.getAllSimilarMovieAsLiveData(id)
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