package ru.firstSet.kotlinOne

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesViewAdapter(private val someClickListener: SomeInterfaceClickListener, val movieList: List<Movie>) :
    RecyclerView.Adapter<MoviesViewAdapter.MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position));
        holder.itemView.setOnClickListener {
            someClickListener.onClick(position)
        }
    }






    override fun getItemCount(): Int = movieList.size

    fun getItem(position: Int): Movie = movieList[position]

    class MoviesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textViewSomeId: TextView = itemView.findViewById(R.id.fmlSomeId)
        private val textViewMinuteTime: TextView = itemView.findViewById(R.id.fmlTextViewMinuteTime)
        private val textViewNameMovie: TextView = itemView.findViewById(R.id.fmlNameMovie)
        private val textViewTag: TextView = itemView.findViewById(R.id.fmlTag)
        private val textViewReview: TextView = itemView.findViewById(R.id.fmlTextViewReview)
        private val imageViewMovieOrig: ImageView = itemView.findViewById(R.id.fmlNameImageViewOrig)
        private val imageViewLike: ImageView = itemView.findViewById(R.id.fmlIsLike)
        private val ratingBarRating: RatingBar = itemView.findViewById(R.id.fmlRatingBar)

        fun bind(movie: Movie) {
            if (movie.nameImageView != null)
                this.imageViewMovieOrig.setImageResource(movie.nameImageView)
            if (movie.isLike) this.imageViewLike.setImageResource(R.drawable.like_red)
            else imageViewLike.setImageResource(R.drawable.like)
            if (movie.ratingBarRating != null)
                this.ratingBarRating.rating = movie.ratingBarRating.toFloat()
            this.textViewSomeId.text = movie.someId
            this.textViewMinuteTime.text = movie.minuteTime
            this.textViewNameMovie.text = movie.nameMovie
            this.textViewTag.text = movie.tag
            this.textViewReview.text = movie.review
        }
    }
interface SomeInterfaceClickListener {
    fun onClick(id: Int)
}
}

