package ru.movie.searching.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import ru.movie.searching.R
import ru.movie.searching.data.entity.MovieModel
import ru.movie.searching.utils.ImageTransformation
import ru.movie.searching.utils.Constants

class MovieListAdapter(var onMovieListener: OnMovieListener) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var movieList = emptyList<MovieModel>();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieList[position]

        getMoviePicture(currentItem.poster_path, holder.itemView.movie_picture)
        holder.itemView.movie_name.text = currentItem.title
        holder.itemView.movie_rating.rating = currentItem.vote_average.toFloat() / 2

        holder.bind(currentItem, onMovieListener)
    }

    fun setMoviesData(movieList: List<MovieModel>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    private fun getMoviePicture(path: String, imageView: ImageView) {
        val url = Constants.BASE_POSTER_URL + path
        Picasso.get()
            .load(url)
            .transform(ImageTransformation())
            .into(imageView)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MovieModel, onMovieListener: OnMovieListener) {
            itemView.setOnClickListener { onMovieListener.onMovieClick(item) }
        }
    }

    interface OnMovieListener {
        fun onMovieClick(movie: MovieModel)
    }
}