package com.example.mytokdismovieapp.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import kotlinx.android.synthetic.main.activity_movie.*
import java.util.ArrayList

class MovieActivity : AppCompatActivity(){

    private var movieAdapter: MovieAdapter? = null
    private var viewModelMovie: MovieViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movieAdapter =  MovieAdapter(this, ArrayList(0))
        loadRecycler()
        loadMovie()

    }

    private val getNewsRepository: Observer<List<ResultsItemMovie>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateMovie(movieItems as List<ResultsItemMovie>)
            progressBar.visibility = View.INVISIBLE

        }
    }

    private fun loadMovie() {
        viewModelMovie = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        progressBar.visibility = View.VISIBLE
        viewModelMovie?.getAllMovie(1)
        viewModelMovie?.remoteRepository?.observe(this, getNewsRepository)
    }

    private fun loadRecycler() {
        val layoutManager = LinearLayoutManager(this)
        recyclerMovie.layoutManager = layoutManager
        recyclerMovie.adapter = movieAdapter
        recyclerMovie.setHasFixedSize(true)
    }

}