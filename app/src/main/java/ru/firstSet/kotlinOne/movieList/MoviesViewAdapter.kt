package ru.firstSet.kotlinOne.movieList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.firstSet.kotlinOne.data.Movie
import com.bumptech.glide.Glide
import ru.firstSet.kotlinOne.R

class MoviesViewAdapter(val someClickListener: (Long) -> Unit) :
    RecyclerView.Adapter<MoviesViewAdapter.MoviesViewHolder>() {
    var movieList: List<Movie> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position.toInt()));
        holder.itemView.setOnClickListener {
            someClickListener(position.toLong())
        }
    }

    fun bindMovie(movieList: List<Movie>) {
        this.movieList = movieList
        notifyDataSetChanged()
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
        private val imageViewMovieOrig: ImageView = itemView.findViewById(R.id.fmlImageOrig)
        private val imageViewLike: ImageView = itemView.findViewById(R.id.fmlIsLike)
        private val ratingBarRating: RatingBar = itemView.findViewById(R.id.fmlRatingBar)

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
                textViewSomeId.text = movie.adult.toString()+"+"
            textViewMinuteTime.text =
                movie.runtime.toString() + " " + itemView.context.getString(R.string.fmlTextViewMin)
            textViewNameMovie.text = movie.title
            Glide
                .with(itemView)
                .load(movie.posterPicture)
                .into(this.imageViewMovieOrig)
            textViewTag.text = movie.genres.joinToString(separator = ", ") { it.name }
            if (movie.adult==16) { imageViewLike.setImageResource(R.drawable.like_red) }
            else { imageViewLike.setImageResource(R.drawable.like) }
            textViewReview.text =
                movie.vote_count.toString() + " " + itemView.context.getString(R.string.textViewReview)
            ratingBarRating.rating = movie.ratings / 2
        }
    }
}


