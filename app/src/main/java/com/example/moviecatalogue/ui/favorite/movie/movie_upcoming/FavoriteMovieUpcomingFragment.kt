package com.example.moviecatalogue.ui.favorite.movie.movie_upcoming


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
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.local.entity.MovieUpcomingEntity
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.ui.favorite.movie.FavoriteViewModel
import com.example.moviecatalogue.ui.favorite.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import java.util.*

class FavoriteMovieUpcomingFragment : Fragment() {

    private var movieAdapter: FavoriteMovieUpcomingAdapter? = null
    private var viewModelMovie: FavoriteMovieUpcomingViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var movieUpcomingEntity = MovieUpcomingEntity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelMovie = obtainMovieUpcomingViewModel(activity as AppCompatActivity)
        viewModelFavorite = obtainFavoriteViewModel(activity as AppCompatActivity)

        viewModelMovie?.getAllMovieUpcoming?.observe(this, movieObserver)

        loadAdapter()
        loadGenre()
        loadRecyclerview()

    }

    private val movieObserver: Observer<PagedList<MovieUpcomingEntity>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.submitList(movieItems)
        }
    }

    private val getResultItemGenre: Observer<List<ResultsItemGenre>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateGenre(movieItems as List<ResultsItemGenre>)

        }
    }

    private fun loadAdapter() {
        movieAdapter = activity?.let { it1 -> FavoriteMovieUpcomingAdapter(it1, ArrayList(0)) {

            val movieId: Int? = it.movieMovieUpcomingId

            movieUpcomingEntity.movieMovieUpcomingId = movieId

            viewModelFavorite?.deleteMovieUpcoming(movieUpcomingEntity)

            Toast.makeText(context, "Delete to Favorite : " + it.movieMovieUpcomingTitle, Toast.LENGTH_SHORT).show()

        }

        }
    }

    private fun loadRecyclerview () {
        recyclerFavoriteMovie.layoutManager = LinearLayoutManager(context)
        recyclerFavoriteMovie.setHasFixedSize(true)
        recyclerFavoriteMovie.adapter = movieAdapter

    }

    private fun loadGenre() {
        viewModelMovie?.getAllGenre()
        viewModelMovie?.resultItemGenre?.observe(this, getResultItemGenre)
    }

    private fun obtainMovieUpcomingViewModel(activity: FragmentActivity): FavoriteMovieUpcomingViewModel? {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(FavoriteMovieUpcomingViewModel::class.java)
    }

    private fun obtainFavoriteViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory: ViewModelFactory? = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
    }

}
