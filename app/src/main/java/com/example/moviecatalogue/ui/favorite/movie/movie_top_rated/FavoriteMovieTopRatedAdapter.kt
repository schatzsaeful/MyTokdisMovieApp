package com.example.moviecatalogue.ui.favorite.movie.movie_top_rated

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
import com.example.moviecatalogue.data.source.local.entity.MovieTopRatedEntity
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.ui.favorite.detail.DetailFavoriteMovieTopRatedActivity
import kotlinx.android.synthetic.main.movie_item.view.*

class FavoriteMovieTopRatedAdapter internal constructor(private val activity: Activity, private val resultItemGenre: MutableList<ResultsItemGenre>, private val listener: (MovieTopRatedEntity) -> Unit) : PagedListAdapter<MovieTopRatedEntity, FavoriteMovieTopRatedAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movieEntity = getItem(position)
        movieEntity?.let { holder.bind(it, listener) }

        if (movieEntity != null) {

            Glide.with(holder.itemView.context)
                .load(BuildConfig.POSTER_URL + movieEntity.movieMovieTopRatedPoster)
                .placeholder(R.drawable.image)
                .error(R.drawable.broken_image)
                .into(holder.imagePoster)

            holder.textTitle.text = movieEntity.movieMovieTopRatedTitle
            holder.textRelease.text = movieEntity.movieMovieTopRatedRelease
            holder.textVoteAverage.text = movieEntity.movieMovieTopRatedVoteAverage.toString()
            holder.textVoteCount.text = movieEntity.movieMovieTopRatedVoteCount.toString()

            holder.itemView.setOnClickListener {
                val intent = Intent(activity, DetailFavoriteMovieTopRatedActivity::class.java)
                intent.putExtra(DetailFavoriteMovieTopRatedActivity.EXTRA_MOVIE, movieEntity)
                intent.putExtra(DetailFavoriteMovieTopRatedActivity.EXTRA_GENRE, movieEntity.movieMovieTopRatedGenre)
                activity.startActivity(intent)
            }

        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultMovie: MovieTopRatedEntity, listener: (MovieTopRatedEntity) -> Unit) {

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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieTopRatedEntity> = object : DiffUtil.ItemCallback<MovieTopRatedEntity>() {

            override fun areItemsTheSame(oldNote: MovieTopRatedEntity, newNote: MovieTopRatedEntity): Boolean {
                return oldNote.movieMovieTopRatedId == newNote.movieMovieTopRatedId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: MovieTopRatedEntity, newNote: MovieTopRatedEntity): Boolean {
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