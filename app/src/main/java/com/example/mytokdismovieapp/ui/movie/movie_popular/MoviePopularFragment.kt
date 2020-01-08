package com.example.mytokdismovieapp.ui.movie.movie_popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.local.entity.MoviePopularEntity
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import com.example.mytokdismovieapp.ui.favorite.movie.FavoriteViewModel
import com.example.mytokdismovieapp.ui.favorite.viewmodel.ViewModelFactory
import com.example.mytokdismovieapp.utils.DateHelper
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

class MoviePopularFragment : Fragment(){

    private var movieAdapter: MoviePopularAdapter? = null
    private var viewModelMovie: MoviePopularViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var moviePopularEntity = MoviePopularEntity()

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

        viewModelMovie = ViewModelProviders.of(this).get(MoviePopularViewModel::class.java)

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
            MoviePopularAdapter(
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

                moviePopularEntity.movieMoviePopularId = movieId
                moviePopularEntity.movieMoviePopularPoster = moviePoster
                moviePopularEntity.movieMoviePopularTitle = movieTitle
                moviePopularEntity.movieMoviePopularRelease =
                    movieRelease?.let { it1 -> DateHelper.formatDateToMatch(it1) }
                moviePopularEntity.movieMoviePopularVoteAverage = movieVoteAverage
                moviePopularEntity.movieMoviePopularVoteCount = movieVoteCount
                moviePopularEntity.movieMoviePopularOverview = movieOverview
                moviePopularEntity.movieMoviePopularGenre = movieGenre.toString()

                viewModelFavorite?.insertMoviePopular(moviePopularEntity)

                Toast.makeText(context, "Add to Favorite : " + it.title, Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadMovie() {
        progressBarSimilar.visibility = View.VISIBLE
        viewModelMovie?.getAllPopularMovie(3)
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

    private fun obtainViewModel(activity: FragmentActivity): FavoriteViewModel {
        val factory: ViewModelFactory? = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
    }

}
