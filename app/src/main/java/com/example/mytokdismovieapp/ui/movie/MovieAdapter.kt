package com.example.mytokdismovieapp.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytokdismovieapp.BuildConfig
import com.example.mytokdismovieapp.R
import com.example.mytokdismovieapp.data.source.remote.response.ResultsItemMovie
import com.example.mytokdismovieapp.ui.detail.DetailMovieActivity
import com.example.mytokdismovieapp.utils.DateHelper
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(private var mContext: Context, private var resultsItemMovie: MutableList<ResultsItemMovie>) : PagedListAdapter<ResultsItemMovie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int = resultsItemMovie.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(resultsItemMovie[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultMovie: ResultsItemMovie) {

            Glide.with(mContext)
                .load(BuildConfig.POSTER_URL + resultMovie.posterPath)
                .placeholder(R.drawable.image)
                .error(R.drawable.broken_image)
                .into(itemView.imagePoster)

            itemView.textTitle.text = resultMovie.title
            itemView.textRelease.text = resultMovie.releaseDate?.let { DateHelper.formatDateToMatch(it) }
            itemView.textVoteAverage.text = resultMovie.voteAverage.toString()
            itemView.textVoteCount.text = resultMovie.voteCount.toString()

            itemView.setOnClickListener {
                val intent = Intent(mContext, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, resultMovie)
                mContext.startActivity(intent)

            }

        }
    }

    fun updateMovie(item: List<ResultsItemMovie>) {
        resultsItemMovie.clear()
        resultsItemMovie.addAll(item)
        notifyDataSetChanged()
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ResultsItemMovie> = object : DiffUtil.ItemCallback<ResultsItemMovie>() {

            override fun areItemsTheSame(oldNote: ResultsItemMovie, newNote: ResultsItemMovie): Boolean {
                return oldNote.id == newNote.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: ResultsItemMovie, newNote: ResultsItemMovie): Boolean {
                return oldNote == newNote
            }
        }
    }

}