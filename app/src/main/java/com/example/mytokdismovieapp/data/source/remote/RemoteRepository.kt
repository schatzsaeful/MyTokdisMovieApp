package com.example.mytokdismovieapp.data.source.remote

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytokdismovieapp.BuildConfig
import com.example.mytokdismovieapp.data.source.remote.api.ApiUtils
import com.example.mytokdismovieapp.data.source.remote.api.BaseApiService
import com.example.mytokdismovieapp.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository  {
    private val baseApiService: BaseApiService? = ApiUtils.service

    fun getAllPopularMovieAsLiveData(page: Int): LiveData<List<ResultsItemMovie>>? {
        val resultsItemMovie = MutableLiveData<List<ResultsItemMovie>>()
        baseApiService?.getPopularMovie(BuildConfig.API_KEY, "en-US", page)
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

    fun getAllTopRatedMovieAsLiveData(page: Int): LiveData<List<ResultsItemMovie>>? {
        val resultsItemMovie = MutableLiveData<List<ResultsItemMovie>>()
        baseApiService?.getTopRatedMovie(BuildConfig.API_KEY, "en-US", page)
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

    fun getAllUpcomingMovieAsLiveData(page: Int): LiveData<List<ResultsItemMovie>>? {
        val resultsItemMovie = MutableLiveData<List<ResultsItemMovie>>()
        baseApiService?.getUpcomingMovie(BuildConfig.API_KEY, "en-US", page)
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

    fun getAllNowPlayingMovieAsLiveData(page: Int): LiveData<List<ResultsItemMovie>>? {
        val resultsItemMovie = MutableLiveData<List<ResultsItemMovie>>()
        baseApiService?.getNowPlayingMovie(BuildConfig.API_KEY, "en-US", page)
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

    fun getAllGenreMovieAsLiveData(): LiveData<List<ResultsItemGenre>>? {
        val resultsItemGenre = MutableLiveData<List<ResultsItemGenre>>()
        baseApiService?.getGenre(BuildConfig.API_KEY, "en-US")
            ?.enqueue(object : Callback<ResponseGenre?> {
                override fun onResponse(@NonNull call: Call<ResponseGenre?>?, @NonNull response: Response<ResponseGenre?>) {
                    if (response.isSuccessful) {
                        val moviesResponse: ResponseGenre? = response.body()
                        if (moviesResponse?.genres != null) {
                            resultsItemGenre.value = moviesResponse.genres as ArrayList<ResultsItemGenre>?
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseGenre?>?, t: Throwable?) {

                }
            })

        return resultsItemGenre
    }

    fun getAllSimilarMovieAsLiveData(id: Int): LiveData<List<ResultsItemMovie>>? {
        val resultsItemCast = MutableLiveData<List<ResultsItemMovie>>()
        baseApiService?.getSimilarMovie(id, BuildConfig.API_KEY,"en-US")
            ?.enqueue(object : Callback<ResponseMovie?> {
                override fun onResponse(@NonNull call: Call<ResponseMovie?>?, @NonNull response: Response<ResponseMovie?>) {
                    if (response.isSuccessful) {
                        val moviesResponse: ResponseMovie? = response.body()
                        if (moviesResponse?.results != null) {
                            resultsItemCast.value = moviesResponse.results as ArrayList<ResultsItemMovie>?
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseMovie?>?, t: Throwable?) {

                }
            })

        return resultsItemCast
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