package com.example.mytokdismovieapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mytokdismovieapp.BuildConfig
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import com.example.mytokdismovieapp.utils.DateHelper
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        loadActionbar()
        loadMovie()

    }

    private fun loadMovie() {
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as ResultsItemMovie

        Glide.with(this)
            .load(BuildConfig.POSTER_URL + movie.posterPath)
            .placeholder(R.drawable.image)
            .error(R.drawable.broken_image)
            .into(imagePoster)

        textTitle.text = movie.title
        textRelease.text = movie.releaseDate?.let { DateHelper.formatDateToMatch(it) }
        textVoteAverage.text = movie.voteAverage.toString()
        textVoteCount.text = movie.voteCount.toString()
        textOverview.text = movie.overview

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

}
