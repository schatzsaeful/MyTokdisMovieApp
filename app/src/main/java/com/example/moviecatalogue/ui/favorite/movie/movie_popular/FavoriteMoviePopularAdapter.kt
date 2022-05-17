package com.example.moviecatalogue.ui.favorite.movie.movie_popular

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.BuildConfig
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.local.entity.MoviePopularEntity
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.ui.favorite.detail.DetailFavoriteMoviePopularActivity
import kotlinx.android.synthetic.main.movie_item.view.*

class FavoriteMoviePopularAdapter internal constructor(private val activity: Activity, private val resultItemGenre: MutableList<ResultsItemGenre>, private val listener: (MoviePopularEntity) -> Unit) : PagedListAdapter<MoviePopularEntity, FavoriteMoviePopularAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movieEntity = getItem(position)
        movieEntity?.let { holder.bind(it, listener) }

        if (movieEntity != null) {

            Glide.with(holder.itemView.context)
                .load(BuildConfig.POSTER_URL + movieEntity.movieMoviePopularPoster)
                .placeholder(R.drawable.image)
                .error(R.drawable.broken_image)
                .into(holder.imagePoster)

            holder.textTitle.text = movieEntity.movieMoviePopularTitle
            holder.textRelease.text = movieEntity.movieMoviePopularRelease
            holder.textVoteAverage.text = movieEntity.movieMoviePopularVoteAverage.toString()
            holder.textVoteCount.text = movieEntity.movieMoviePopularVoteCount.toString()

            holder.itemView.setOnClickListener {
                val intent = Intent(activity, DetailFavoriteMoviePopularActivity::class.java)
                intent.putExtra(DetailFavoriteMoviePopularActivity.EXTRA_MOVIE, movieEntity)
                intent.putExtra(DetailFavoriteMoviePopularActivity.EXTRA_GENRE, movieEntity.movieMoviePopularGenre)
                activity.startActivity(intent)
            }

        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultMovie: MoviePopularEntity, listener: (MoviePopularEntity) -> Unit) {

            itemView.imageFavorite.setOnClickListener {
                listener(resultMovie)
            }

        }

        val imagePoster: ImageView = itemView.imagePoster
        val textTitle: TextView = itemView.textTitle
        val textRelease: TextView = itemView.textRelease
        val textVoteAverage: TextView = itemView.textVoteAverage
        val textVoteCount: TextView = itemView.textVoteCount

    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MoviePopularEntity> = object : DiffUtil.ItemCallback<MoviePopularEntity>() {

            override fun areItemsTheSame(oldNote: MoviePopularEntity, newNote: MoviePopularEntity): Boolean {
                return oldNote.movieMoviePopularId == newNote.movieMoviePopularId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: MoviePopularEntity, newNote: MoviePopularEntity): Boolean {
                return oldNote == newNote
            }
        }
    }

    fun updateGenre(genre: List<ResultsItemGenre>) {
        resultItemGenre.clear()
        resultItemGenre.addAll(genre)
        notifyDataSetChanged()
    }

}