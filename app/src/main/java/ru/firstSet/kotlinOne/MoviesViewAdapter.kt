package ru.firstSet.kotlinOne

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesViewAdapter(context: Context, var moviesList: List<Movie>) :
    RecyclerView.Adapter<MoviesViewAdapter.MoviesViewHolder>() {
    private val inflater : LayoutInflater
    private val moviesLists: List<Movie>
    init {
        inflater = LayoutInflater.from(context)
        moviesLists = moviesList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view: View =  inflater.inflate(R.layout.view_holder_movie, parent, false)
//        val view: View =  inflater.inflate(R.layout.other, parent, false)
        return MoviesViewHolder(view)

//        return MoviesViewHolder(view)
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position));
    }

    override fun getItemCount()
            : Int = moviesLists.size

    fun getItem(position: Int): Movie = moviesLists[position]


    class MoviesViewHolder( itemView:View) :
//    RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_movie, parent, false)) {
        RecyclerView.ViewHolder(itemView) {
        private val textViewSomeId: TextView
        private val textViewMinuteTime: TextView
        private val textViewNameMovie: TextView
        private val textViewTag: TextView
        private val textViewReview: TextView
//    private var imageViewMovieOrig: ImageView?=null
//    private var imageViewLike: ImageView?=null
//    private var ratingBarRating: RatingBar?=null

        init {
            this.textViewSomeId = itemView.findViewById(R.id.fmlSomeId)
            this.textViewMinuteTime = itemView.findViewById(R.id.fmlTextViewMinuteTime)
            this.textViewNameMovie = itemView.findViewById(R.id.fmlNameMovie)
            this.textViewTag = itemView.findViewById(R.id.fmlTag)
            this.textViewReview = itemView.findViewById(R.id.fmlTextViewReview)
//        imageViewLike = itemView.findViewById(R.id.fmlIsLike)
//        imageViewMovieOrig = itemView.findViewById(R.id.fmlNameImageViewOrig)
//        ratingBarRating = itemView.findViewById(R.id.fmlRatingBar)
        }


        fun bind(movie: Movie) {
//        imageViewMovieOrig?.setImageDrawable(Drawable.createFromPath(movie.nameImageView))
//        if (movie.isLike) imageViewLike?.setImageDrawable(Drawable.createFromPath("like_red"))
//        else imageViewLike?.setImageDrawable(Drawable.createFromPath("like"))
//        ratingBarRating?.rating = movie.ratingBarRating!!.toFloat()
            this.textViewSomeId.text = movie.someId
            this.textViewMinuteTime.text = movie.minuteTime
            this.textViewNameMovie.text = movie.nameMovie
            this.textViewTag.text = movie.tag
            this.textViewReview.text = movie.review
        }
    }

}


