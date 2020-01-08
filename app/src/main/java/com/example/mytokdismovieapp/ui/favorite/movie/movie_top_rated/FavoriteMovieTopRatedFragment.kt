package com.example.mytokdismovieapp.ui.favorite.movie.movie_top_rated


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
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.local.entity.MovieTopRatedEntity
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre
import com.example.mytokdismovieapp.ui.favorite.movie.FavoriteViewModel
import com.example.mytokdismovieapp.ui.favorite.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import java.util.ArrayList

class FavoriteMovieTopRatedFragment : Fragment() {

    private var movieAdapter: FavoriteMovieTopRatedAdapter? = null
    private var viewModelMovie: FavoriteMovieTopRatedViewModel? = null
    private var viewModelFavorite: FavoriteViewModel? = null
    private var movieUpcomingEntity = MovieTopRatedEntity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelMovie = obtainMovieTopRatedViewModel(activity as AppCompatActivity)
        viewModelFavorite = obtainFavoriteViewModel(activity as AppCompatActivity)

        viewModelMovie?.getAllMovieTopRated?.observe(this, movieObserver)

        loadAdapter()
        loadGenre()
        loadRecyclerview()

    }

    private val movieObserver: Observer<PagedList<MovieTopRatedEntity>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.submitList(movieItems)
        }
    }

    private val getResultItemGenre: Observer<List<ResultsItemGenre>> = Observer { movieItems ->
        if (movieItems != null) {
            movieAdapter?.updateGenre(movieItems as List<ResultsItemGenre>)

        }
    }

    private fun loadRecyclerview() {
        recyclerFavoriteMovie.layoutManager = LinearLayoutManager(context)
        recyclerFavoriteMovie.setHasFixedSize(true)
        recyclerFavoriteMovie.adapter = movieAdapter
    }

    private fun loadAdapter() {
        movieAdapter = activity?.let { it1 -> FavoriteMovieTopRatedAdapter(it1, ArrayList(0)) {

            val movieId: Int? = it.movieMovieTopRatedId

            movieUpcomingEntity.movieMovieTopRatedId = movieId

            viewModelFavorite?.deleteMovieTopRated(movieUpcomingEntity)

            Toast.makeText(context, "Delete to Favorite : " + it.movieMovieTopRatedTitle, Toast.LENGTH_SHORT).show()

        }

        }
    }

    private fun loadGenre() {
        viewModelMovie?.getAllGenre()
        viewModelMovie?.resultItemGenre?.observe(this, getResultItemGenre)
    }

    private fun obtainMovieTopRatedViewModel(activity: FragmentActivity): FavoriteMovieTopRatedViewModel? {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(FavoriteMovieTopRatedViewModel::class.java)
    }

    private fun obtainFavoriteViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory: ViewModelFactory? = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
    }
}
