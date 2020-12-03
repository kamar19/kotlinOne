package ru.firstSet.kotlinOne

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesViewAdapter(context: Context, var movieList: List<Movie>) :
    RecyclerView.Adapter<MoviesViewAdapter.MoviesViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val moviesLists: List<Movie> = movieList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view: View = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position));
    }

    override fun getItemCount(): Int = moviesLists.size

    fun getItem(position: Int): Movie = moviesLists[position]

    class MoviesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textViewSomeId: TextView = itemView.findViewById(R.id.fmlSomeId)
        private val textViewMinuteTime: TextView = itemView.findViewById(R.id.fmlTextViewMinuteTime)
        private val textViewNameMovie: TextView = itemView.findViewById(R.id.fmlNameMovie)
        private val textViewTag: TextView = itemView.findViewById(R.id.fmlTag)
        private val textViewReview: TextView = itemView.findViewById(R.id.fmlTextViewReview)
        private val imageViewMovieOrig: ImageView = itemView.findViewById(R.id.fmlIsLike)
        private val imageViewLike: ImageView = itemView.findViewById(R.id.fmlNameImageViewOrig)
        private val ratingBarRating: RatingBar = itemView.findViewById(R.id.fmlRatingBar)

        fun bind(movie: Movie) {
            this.imageViewMovieOrig.setImageResource(movie.nameImageView!!)
            if (movie.isLike) this.imageViewLike.setImageResource(R.drawable.like_red)
            else imageViewLike.setImageResource(R.drawable.like)
            this.ratingBarRating.rating = movie.ratingBarRating!!.toFloat()
            this.textViewSomeId.text = movie.someId
            this.textViewMinuteTime.text = movie.minuteTime
            this.textViewNameMovie.text = movie.nameMovie
            this.textViewTag.text = movie.tag
            this.textViewReview.text = movie.review
        }
    }
}


