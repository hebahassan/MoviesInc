package com.example.moviesinc.ui.movies_screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.moviesinc.R
import com.example.moviesinc.model.MovieResult
import kotlinx.android.synthetic.main.row_movie.view.*
import javax.inject.Inject

class MoviesAdapter @Inject constructor(private val requestManager: RequestManager)
    : RecyclerView.Adapter<MoviesAdapter.MovieItemView>() {

    private val moviesList = ArrayList<MovieResult>()

    inner class MovieItemView(itemView: View): RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: MovieResult) {
            requestManager.load(movie.posterPath).into(itemView.moviePoster)
            itemView.movieTitle.text = movie.title
            itemView.movieReleaseDate.text = movie.releaseDate
            itemView.averageRating.text = "${movie.voteAverage} / 10"
        }
    }

    fun updateList(updatedList: List<MovieResult>) {
        moviesList.clear()
        moviesList.addAll(updatedList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemView {
        return MovieItemView(LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false))
    }

    override fun getItemCount(): Int = moviesList.count()

    override fun onBindViewHolder(holder: MovieItemView, position: Int) {
        holder.bind(moviesList[position])
    }
}