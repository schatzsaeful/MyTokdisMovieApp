package com.example.mytokdismovieapp.data.source.remote

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytokdismovieapp.BuildConfig
import com.example.mytokdismovieapp.data.source.remote.api.ApiUtils
import com.example.mytokdismovieapp.data.source.remote.api.BaseApiService
import com.example.mytokdismovieapp.data.source.remote.response.ResponseMovie
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository  {
    private val baseApiService: BaseApiService? = ApiUtils.service

    fun getAllMovieAsLiveData(page: Int): LiveData<List<ResultsItemMovie>>? {
        val resultsItemMovie = MutableLiveData<List<ResultsItemMovie>>()
        baseApiService?.getMovie(BuildConfig.API_KEY, "en-US", page)
            ?.enqueue(object : Callback<ResponseMovie?> {
                override fun onResponse(@NonNull call: Call<ResponseMovie?>?, @NonNull response: Response<ResponseMovie?>) {
                    if (response.isSuccessful) {
                        val moviesResponse: ResponseMovie? = response.body()
                        if (moviesResponse?.results != null) {
                            resultsItemMovie.value = moviesResponse.results as ArrayList<ResultsItemMovie>?
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseMovie?>?, t: Throwable?) {

                }
            })

        return resultsItemMovie
    }

    companion object {
        private var INSTANCE: RemoteRepository? = null
        @JvmStatic
        val instance: RemoteRepository?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = RemoteRepository()
                }
                return INSTANCE
            }
    }

}