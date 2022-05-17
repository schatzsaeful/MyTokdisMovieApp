package com.example.moviecatalogue.ui.movie.movie_upcoming

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.local.entity.MovieUpcomingEntity
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie
import com.example.moviecatalogue.ui.favorite.movie.FavoriteViewModel
import com.example.moviecatalogue.ui.favorite.viewmodel.ViewModelFactory
import com.example.moviecatalogue.utils.DateHelper
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

class MovieUpcomingFragment : Fragment() {

    private var movieAdapter: MovieUpcomingAdapter? = null
    private var viewModelMovie: MovieUpcomingViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var movieUpcomingEntity = MovieUpcomingEntity()

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

        viewModelMovie = ViewModelProviders.of(this).get(MovieUpcomingViewModel::class.java)

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
            MovieUpcomingAdapter(
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
                val movieGenre: String? = it.genreIds.toString()

                movieUpcomingEntity.movieMovieUpcomingId = movieId
                movieUpcomingEntity.movieMovieUpcomingPoster = moviePoster
                movieUpcomingEntity.movieMovieUpcomingTitle = movieTitle
                movieUpcomingEntity.movieMovieUpcomingRelease =
                    movieRelease?.let { it1 -> DateHelper.formatDateToMatch(it1) }
                movieUpcomingEntity.movieMovieUpcomingVoteAverage = movieVoteAverage
                movieUpcomingEntity.movieMovieUpcomingVoteCount = movieVoteCount
                movieUpcomingEntity.movieMovieUpcomingOverview = movieOverview
                movieUpcomingEntity.movieMovieUpcomingGenre = movieGenre

                viewModelFavorite?.insertMovieUpcoming(movieUpcomingEntity)

                Toast.makeText(context, "Add to Favorite : " + it.title, Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadMovie() {
        progressBarSimilar.visibility = View.VISIBLE
        viewModelMovie?.getAllUpcomingMovie(5)
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
