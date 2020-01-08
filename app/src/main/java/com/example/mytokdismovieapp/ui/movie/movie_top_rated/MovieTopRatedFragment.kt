package com.example.mytokdismovieapp.ui.movie.movie_top_rated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.local.entity.MovieTopRatedEntity
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import com.example.mytokdismovieapp.ui.favorite.movie.FavoriteViewModel
import com.example.mytokdismovieapp.ui.favorite.viewmodel.ViewModelFactory
import com.example.mytokdismovieapp.utils.DateHelper
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

class MovieTopRatedFragment : Fragment() {

    private var movieAdapter: MovieTopRatedAdapter? = null
    private var viewModelMovie: MovieTopRatedViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var movieTopRatedEntity = MovieTopRatedEntity()

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

        viewModelMovie = ViewModelProviders.of(this).get(MovieTopRatedViewModel::class.java)

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
            MovieTopRatedAdapter(
                ArrayList(0),
                ArrayList(0)
            ) {

                val movieId: Int? = it.id
                val moviePoster: String? = it.posterPath
                val movieTitle: String? = it.title
                val movieRelease: Date? = it.releaseDate
                val movieVoteAverage: Double? = it.voteAverage
                val movieVoteCount: Int? = it.voteCount
                val movieOverview: String? = it.overview
                val movieGenre: List<Int>? = it.genreIds

                movieTopRatedEntity.movieMovieTopRatedId = movieId
                movieTopRatedEntity.movieMovieTopRatedPoster = moviePoster
                movieTopRatedEntity.movieMovieTopRatedTitle = movieTitle
                movieTopRatedEntity.movieMovieTopRatedRelease =
                    movieRelease?.let { it1 -> DateHelper.formatDateToMatch(it1) }
                movieTopRatedEntity.movieMovieTopRatedVoteAverage = movieVoteAverage
                movieTopRatedEntity.movieMovieTopRatedVoteCount = movieVoteCount
                movieTopRatedEntity.movieMovieTopRatedOverview = movieOverview
                movieTopRatedEntity.movieMovieTopRatedGenre = movieGenre.toString()

                viewModelFavorite?.insertMovieTopRated(movieTopRatedEntity)

                Toast.makeText(context, "Add to Favorite : " + it.title, Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadMovie() {
        progressBarSimilar.visibility = View.VISIBLE
        viewModelMovie?.getAllTopRatedMovie(1)
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
