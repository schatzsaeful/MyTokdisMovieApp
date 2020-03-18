package com.example.mytokdismovieapp.ui.favorite.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mytokdismovieapp.BuildConfig
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.local.entity.MovieNowPlayingEntity
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import com.example.mytokdismovieapp.ui.detail.DetailMovieViewModel
import com.example.mytokdismovieapp.ui.favorite.movie.FavoriteViewModel
import com.example.mytokdismovieapp.ui.favorite.viewmodel.ViewModelFactory
import com.example.mytokdismovieapp.ui.movie.movie_upcoming.MovieUpcomingAdapter
import com.example.mytokdismovieapp.utils.DateHelper
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.util.*

class DetailFavoriteMovieNowPlayingActivity : AppCompatActivity() {

    private var movieAdapter: MovieUpcomingAdapter? = null
    private var viewModelMovie: DetailMovieViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var movieNowPlayingEntity = MovieNowPlayingEntity()



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

                movieNowPlayingEntity.movieNowPlayingId = movieId
                movieNowPlayingEntity.movieNowPlayingPoster = moviePoster
                movieNowPlayingEntity.movieNowPlayingTitle = movieTitle
                movieNowPlayingEntity.movieNowPlayingRelease =
                    movieRelease?.let { it1 -> DateHelper.formatDateToMatch(it1) }
                movieNowPlayingEntity.movieNowPlayingVoteAverage = movieVoteAverage
                movieNowPlayingEntity.movieNowPlayingVoteCount = movieVoteCount
                movieNowPlayingEntity.movieNowPlayingOverview = movieOverview
                movieNowPlayingEntity.movieNowPlayingGenre = movieGenre.toString()

                viewModelFavorite?.insertMovieNowPlaying(movieNowPlayingEntity)

                Toast.makeText(this, "Add Favorite : " + it.title, Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadDetailMovie() {
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieNowPlayingEntity
        val genre = intent.getStringExtra(EXTRA_GENRE)

        Glide.with(this)
            .load(BuildConfig.POSTER_URL + movie.movieNowPlayingPoster)
            .placeholder(R.drawable.image)
            .error(R.drawable.broken_image)
            .into(imagePoster)

        textTitle.text = movie.movieNowPlayingTitle
        textRelease.text = movie.movieNowPlayingRelease
        textVoteAverage.text = movie.movieNowPlayingVoteAverage.toString()
        textVoteCount.text = movie.movieNowPlayingVoteCount.toString()
        textGenre.text = genre
        textOverview.text = movie.movieNowPlayingOverview

    }

    private val getResultItemSimilarMovie: Observer<List<ResultsItemMovie>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateMovie(movieItems as List<ResultsItemMovie>)
            progressBarSimilar.visibility = View.INVISIBLE
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            constrain_DetailMovie.visibility = View.VISIBLE

        }
    }

    private val getResultItemGenre: Observer<List<ResultsItemGenre>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateGenre(movieItems as List<ResultsItemGenre>)

        }
    }

    private fun loadSimilarMovie() {
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieNowPlayingEntity

        progressBarSimilar.visibility = View.VISIBLE

        movie.movieNowPlayingId?.let { viewModelMovie?.getAllSimilarMovie(it) }
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
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieNowPlayingEntity

        supportActionBar?.title = movie.movieNowPlayingTitle
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
