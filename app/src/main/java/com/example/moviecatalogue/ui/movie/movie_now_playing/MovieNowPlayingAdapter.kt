package com.example.moviecatalogue.ui.movie.movie_now_playing

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.BuildConfig
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.remote.response.ResultsItemGenre
import com.example.moviecatalogue.data.source.remote.response.ResultsItemMovie
import com.example.moviecatalogue.ui.detail.DetailMovieNowPlayingActivity
import com.example.moviecatalogue.utils.DateHelper
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieNowPlayingAdapter(private var resultsItemMovie: MutableList<ResultsItemMovie>, private val resultItemGenre: MutableList<ResultsItemGenre>, private val listener: (ResultsItemMovie, ImageView) -> Unit) : PagedListAdapter<ResultsItemMovie, MovieNowPlayingAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int = resultsItemMovie.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(resultsItemMovie[position], listener)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultMovie: ResultsItemMovie, listener: (ResultsItemMovie, ImageView) -> Unit) {

            Glide.with(itemView.context)
                .load(BuildConfig.POSTER_URL + resultMovie.posterPath)
                .placeholder(R.drawable.image)
                .error(R.drawable.broken_image)
                .into(itemView.imagePoster)

            itemView.textTitle.text = resultMovie.title
            itemView.textRelease.text = resultMovie.releaseDate?.let { DateHelper.formatDateToMatch(it) }
            itemView.textVoteAverage.text = resultMovie.voteAverage.toString()
            itemView.textVoteCount.text = resultMovie.voteCount.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailMovieNowPlayingActivity::class.java)
                intent.putExtra(DetailMovieNowPlayingActivity.EXTRA_MOVIE, resultMovie)
                intent.putExtra(DetailMovieNowPlayingActivity.EXTRA_GENRE, resultMovie.genreIds?.let { getGenres(it) })
                itemView.context.startActivity(intent)

            }

            itemView.imageFavorite.setOnClickListener {
                listener(resultMovie, itemView.imageFavorite.findViewById(R.id.imageFavorite))
            }

        }
    }

    fun updateMovie(movie: List<ResultsItemMovie>) {
        resultsItemMovie.clear()
        resultsItemMovie.addAll(movie)
        notifyDataSetChanged()
    }

    fun updateGenre(genre: List<ResultsItemGenre>) {
        resultItemGenre.clear()
        resultItemGenre.addAll(genre)
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

    private fun getGenres(genreIds: List<Int>): String? {
        val movieGenres: MutableList<String?> = ArrayList()
        for (genreId in genreIds) {
            for (genre in resultItemGenre) {
                if (genre.id == genreId) {
                    movieGenres.add(genre.name)
                    break
                }
            }
        }

        return TextUtils.join(", ", movieGenres)
    }

}