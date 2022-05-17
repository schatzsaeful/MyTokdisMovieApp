package com.example.moviecatalogue.ui.favorite.movie.movie_now_playing

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
import com.example.moviecatalogue.data.source.local.entity.MovieNowPlayingEntity
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.ui.favorite.detail.DetailFavoriteMovieNowPlayingActivity
import kotlinx.android.synthetic.main.movie_item.view.*

class FavoriteMovieNowPlayingAdapter internal constructor(private val activity: Activity, private val resultItemGenre: MutableList<ResultsItemGenre>, private val listener: (MovieNowPlayingEntity) -> Unit) : PagedListAdapter<MovieNowPlayingEntity, FavoriteMovieNowPlayingAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movieEntity = getItem(position)
        movieEntity?.let { holder.bind(it, listener) }

        if (movieEntity != null) {

            Glide.with(holder.itemView.context)
                .load(BuildConfig.POSTER_URL + movieEntity.movieNowPlayingPoster)
                .placeholder(R.drawable.image)
                .error(R.drawable.broken_image)
                .into(holder.imagePoster)

            holder.textTitle.text = movieEntity.movieNowPlayingTitle
            holder.textRelease.text = movieEntity.movieNowPlayingRelease
            holder.textVoteAverage.text = movieEntity.movieNowPlayingVoteAverage.toString()
            holder.textVoteCount.text = movieEntity.movieNowPlayingVoteCount.toString()

            holder.itemView.setOnClickListener {
                val intent = Intent(activity, DetailFavoriteMovieNowPlayingActivity::class.java)
                intent.putExtra(DetailFavoriteMovieNowPlayingActivity.EXTRA_MOVIE, movieEntity)
                intent.putExtra(DetailFavoriteMovieNowPlayingActivity.EXTRA_GENRE, movieEntity.movieNowPlayingGenre)
                activity.startActivity(intent)
            }

        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultMovie: MovieNowPlayingEntity, listener: (MovieNowPlayingEntity) -> Unit) {

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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieNowPlayingEntity> = object : DiffUtil.ItemCallback<MovieNowPlayingEntity>() {

            override fun areItemsTheSame(oldNote: MovieNowPlayingEntity, newNote: MovieNowPlayingEntity): Boolean {
                return oldNote.movieNowPlayingId == newNote.movieNowPlayingId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: MovieNowPlayingEntity, newNote: MovieNowPlayingEntity): Boolean {
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