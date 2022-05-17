package com.example.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviecatalogue.BuildConfig
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.local.entity.MoviePopularEntity
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie
import com.example.moviecatalogue.ui.favorite.movie.FavoriteViewModel
import com.example.moviecatalogue.ui.favorite.viewmodel.ViewModelFactory
import com.example.moviecatalogue.ui.movie.movie_upcoming.MovieUpcomingAdapter
import com.example.moviecatalogue.utils.DateHelper
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.util.*

class DetailMoviePopularActivity : AppCompatActivity() {

    private var movieAdapter: MovieUpcomingAdapter? = null
    private var viewModelMovie: DetailMovieViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var moviePopularEntity = MoviePopularEntity()

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_GENRE = "extra_genre"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        shimmer.startShimmer()

        viewModelFavorite = obtainFavoriteViewModel(this)

        viewModelMovie = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)

        loadAdapter()
        loadDetailMovie()
        loadActionbar()
        loadRecyclerSimilar()
        loadSimilarMovie()
        loadGenre()

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

                Toast.makeText(this, "Add Favorite : " + it.title, Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadDetailMovie() {
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as ResultsItemMovie
        val genre = intent.getStringExtra(EXTRA_GENRE)

        Glide.with(this)
            .load(BuildConfig.POSTER_URL + movie.posterPath)
            .placeholder(R.drawable.image)
            .error(R.drawable.broken_image)
            .into(imagePoster)

        textTitle.text = movie.title
        textRelease.text = movie.releaseDate?.let { DateHelper.formatDateToMatch(it) }
        textVoteAverage.text = movie.voteAverage.toString()
        textVoteCount.text = movie.voteCount.toString()
        textGenre.text = genre
        textOverview.text = movie.overview

    }

    private val getResultItemSimilarMovie: Observer<List<ResultsItemMovie>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateMovie(movieItems as List<ResultsItemMovie>)
            progressBarSimilar.visibility = View.INVISIBLE

        } else {
            text_empty.visibility = View.VISIBLE

        }
    }

    private val getResultItemGenre: Observer<List<ResultsItemGenre>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateGenre(movieItems as List<ResultsItemGenre>)
            progressBarSimilar.visibility = View.INVISIBLE
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            constrain_DetailMovie.visibility = View.VISIBLE

        }
    }

    private fun loadSimilarMovie() {
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as ResultsItemMovie

        progressBarSimilar.visibility = View.VISIBLE

        if (movie.id?.let { viewModelMovie?.getAllSimilarMovie(it) } == null) {
            text_empty.visibility = View.VISIBLE

        }

        movie.id?.let { viewModelMovie?.getAllSimilarMovie(it) }
        viewModelMovie?.resultItemSimilar?.observe(this, getResultItemSimilarMovie)
    }

    private fun loadGenre() {
        viewModelMovie?.getAllGenre()
        viewModelMovie?.resultItemGenre?.observe(this, getResultItemGenre)
    }

    private fun loadRecyclerSimilar() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerSimilar.layoutManager = layoutManager
        recyclerSimilar.adapter = movieAdapter
        recyclerSimilar.setHasFixedSize(true)
    }

    private fun loadActionbar() {
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as ResultsItemMovie

        supportActionBar?.title = movie.title
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun obtainFavoriteViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory: ViewModelFactory? = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
    }

}