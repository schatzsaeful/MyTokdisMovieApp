package com.example.mytokdismovieapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mytokdismovieapp.data.source.remote.RemoteRepository.Companion.instance
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie

class MovieViewModel: ViewModel() {

    var remoteRepository: LiveData<List<ResultsItemMovie>>? = null

    fun getAllMovie(page: Int) {
        if (remoteRepository != null) {
            return
        }

        val newsRepository = instance
        if (newsRepository != null) {
            this.remoteRepository = newsRepository.getAllMovieAsLiveData(page)
        }
    }

}