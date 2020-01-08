package com.example.mytokdismovieapp.ui.favorite.movie.movie_upcoming

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytokdismovieapp.BuildConfig
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.local.entity.MovieUpcomingEntity
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemGenre
import com.example.mytokdismovieapp.ui.favorite.detail.DetailFavoriteMovieUpcomingActivity
import kotlinx.android.synthetic.main.movie_item.view.*

class FavoriteMovieUpcomingAdapter internal constructor(private val activity: Activity, private val resultItemGenre: MutableList<ResultsItemGenre>, private val listener: (MovieUpcomingEntity) -> Unit) : PagedListAdapter<MovieUpcomingEntity, FavoriteMovieUpcomingAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movieEntity = getItem(position)
        movieEntity?.let { holder.bind(it, listener) }

        if (movieEntity != null) {

            Glide.with(holder.itemView.context)
                .load(BuildConfig.POSTER_URL + movieEntity.movieMovieUpcomingPoster)
                .placeholder(R.drawable.image)
                .error(R.drawable.broken_image)
                .into(holder.imagePoster)

            holder.textTitle.text = movieEntity.movieMovieUpcomingTitle
            holder.textRelease.text = movieEntity.movieMovieUpcomingRelease
            holder.textVoteAverage.text = movieEntity.movieMovieUpcomingVoteAverage.toString()
            holder.textVoteCount.text = movieEntity.movieMovieUpcomingVoteCount.toString()

            holder.itemView.setOnClickListener {
                val intent = Intent(activity, DetailFavoriteMovieUpcomingActivity::class.java)
                intent.putExtra(DetailFavoriteMovieUpcomingActivity.EXTRA_MOVIE, movieEntity)
                intent.putExtra(DetailFavoriteMovieUpcomingActivity.EXTRA_GENRE, movieEntity.movieMovieUpcomingGenre)
                activity.startActivity(intent)
            }

        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultMovie: MovieUpcomingEntity, listener: (MovieUpcomingEntity) -> Unit) {

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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieUpcomingEntity> = object : DiffUtil.ItemCallback<MovieUpcomingEntity>() {

            override fun areItemsTheSame(oldNote: MovieUpcomingEntity, newNote: MovieUpcomingEntity): Boolean {
                return oldNote.movieMovieUpcomingId == newNote.movieMovieUpcomingId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: MovieUpcomingEntity, newNote: MovieUpcomingEntity): Boolean {
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