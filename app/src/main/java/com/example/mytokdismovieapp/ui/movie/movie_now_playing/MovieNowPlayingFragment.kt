package com.example.mytokdismovieapp.ui.movie.movie_now_playing

import android.graphics.ColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.local.entity.MovieNowPlayingEntity
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import com.example.mytokdismovieapp.ui.favorite.movie.FavoriteViewModel
import com.example.mytokdismovieapp.ui.favorite.viewmodel.ViewModelFactory
import com.example.mytokdismovieapp.utils.DateHelper
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

class MovieNowPlayingFragment: Fragment() {

    private var movieAdapter: MovieNowPlayingAdapter? = null
    private var viewModelMovie: MovieNowPlayingViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var movieNowPlayingEntity = MovieNowPlayingEntity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelFavorite = obtainViewModel(activity as AppCompatActivity)

        viewModelMovie = ViewModelProviders.of(this).get(MovieNowPlayingViewModel::class.java)

        loadAdapter()
        loadRecycler()
        loadMovie()
        loadGenre()

    }

    private val getResultItemMovie: Observer<List<ResultsItemMovie>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateMovie(movieItems as List<ResultsItemMovie>)
            progressBarSimilar.visibility = View.INVISIBLE

        }
    }

    private val getResultItemGenre: Observer<List<ResultsItemGenre>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateGenre(movieItems as List<ResultsItemGenre>)

        }
    }

    private fun loadAdapter() {
        movieAdapter =
            MovieNowPlayingAdapter(
                ArrayList(0),
                ArrayList(0)
            ) { resultsItemMovie: ResultsItemMovie, imageView: ImageView ->

                val movieId: Int? = resultsItemMovie.id
                val moviePoster: String? = resultsItemMovie.posterPath
                val movieTitle: String? = resultsItemMovie.title
                val movieRelease: Date? = resultsItemMovie.releaseDate
                val movieVoteAverage: Double? = resultsItemMovie.voteAverage
                val movieVoteCount: Int? = resultsItemMovie.voteCount
                val movieOverview: String? = resultsItemMovie.overview
                val movieGenre: List<Int>? = resultsItemMovie.genreIds

                if (movieNowPlayingEntity.movieNowIsFavorite == true) {
                    imageView.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.colorAccentDisable))
                    movieNowPlayingEntity.movieNowPlayingId = movieId
                    movieNowPlayingEntity.movieNowPlayingPoster = moviePoster
                    movieNowPlayingEntity.movieNowPlayingTitle = movieTitle
                    movieNowPlayingEntity.movieNowPlayingRelease =
                        movieRelease?.let { it1 -> DateHelper.formatDateToMatch(it1) }
                    movieNowPlayingEntity.movieNowPlayingVoteAverage = movieVoteAverage
                    movieNowPlayingEntity.movieNowPlayingVoteCount = movieVoteCount
                    movieNowPlayingEntity.movieNowPlayingOverview = movieOverview
                    movieNowPlayingEntity.movieNowPlayingGenre = movieGenre.toString()
                    movieNowPlayingEntity.movieNowIsFavorite = false
                    viewModelFavorite?.deleteMovieNowPlaying(movieNowPlayingEntity)

                    Toast.makeText(context, "Delete to Favorite : " + resultsItemMovie.title, Toast.LENGTH_SHORT).show()

                } else {
                    imageView.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
                    movieNowPlayingEntity.movieNowPlayingId = movieId
                    movieNowPlayingEntity.movieNowPlayingPoster = moviePoster
                    movieNowPlayingEntity.movieNowPlayingTitle = movieTitle
                    movieNowPlayingEntity.movieNowPlayingRelease =
                        movieRelease?.let { it1 -> DateHelper.formatDateToMatch(it1) }
                    movieNowPlayingEntity.movieNowPlayingVoteAverage = movieVoteAverage
                    movieNowPlayingEntity.movieNowPlayingVoteCount = movieVoteCount
                    movieNowPlayingEntity.movieNowPlayingOverview = movieOverview
                    movieNowPlayingEntity.movieNowPlayingGenre = movieGenre.toString()
                    movieNowPlayingEntity.movieNowIsFavorite = true
                    viewModelFavorite?.insertMovieNowPlaying(movieNowPlayingEntity)

                    Toast.makeText(context, "Add to Favorite : " + resultsItemMovie.title, Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun loadMovie() {
        progressBarSimilar.visibility = View.VISIBLE
        viewModelMovie?.getAllNowPlayingMovie(9)
        viewModelMovie?.resultItemMovie?.observe(this, getResultItemMovie)
    }

    private fun loadGenre() {
        viewModelMovie?.getAllGenre()
        viewModelMovie?.resultItemGenre?.observe(this, getResultItemGenre)
    }

    private fun loadRecycler() {
        val layoutManager = LinearLayoutManager(context)
        recyclerMovie.layoutManager = layoutManager
        recyclerMovie.adapter = movieAdapter
        recyclerMovie.setHasFixedSize(true)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory: ViewModelFactory? = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
    }

}
