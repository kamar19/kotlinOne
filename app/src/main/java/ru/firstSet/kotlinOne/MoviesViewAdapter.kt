package ru.firstSet.kotlinOne

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.getSystem
import android.graphics.drawable.Drawable
import android.provider.Settings.Global.getString
import android.provider.Settings.System.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.recyclerview.widget.RecyclerView
import ru.firstSet.kotlinOne.Data.Movie

import com.bumptech.glide.Glide
import ru.firstSet.kotlinOne.DataSource.GenresDataSource
import ru.firstSet.kotlinOne.DataSource.MoviesDataSource

class MoviesViewAdapter(val someClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<MoviesViewAdapter.MoviesViewHolder>() {
    var movieList: List<Movie> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)

        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position));
        holder.itemView.setOnClickListener {
            someClickListener(position)
        }
    }

    fun bindMovie(movieList: List<Movie>) {
        this.movieList = movieList
        notifyDataSetChanged()
        Log.v("bindMovie", this.movieList.size.toString())


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
            textViewSomeId.text = "${movie.minAge}+"
            textViewMinuteTime.text =
                movie.runtime.toString() + " " + itemView.context.getString(R.string.fmlTextViewMin)
            textViewNameMovie.text = movie.title
            Glide
                .with(itemView)
                .load(movie.poster)
                .into(this.imageViewMovieOrig);
            if (movie.ratings != null)
                ratingBarRating.rating = movie.ratings.toFloat()
            textViewTag.text = getStringIntoGenres(movie.genres)
            if (movie.adult) imageViewLike.setImageResource(R.drawable.like_red)
            else imageViewLike.setImageResource(R.drawable.like)
            textViewReview.text =
                movie.votCount.toString() + " " + itemView.context.getString(R.string.textViewReview)
        }

        fun getStringIntoGenres(genres: List<Genre>): String {
            var str: String = ""
            for (idGenres in genres) {
                for (idGenresDataSource in GenresDataSource.getGenresList()) {
                    if (idGenres == idGenresDataSource)
                        str += idGenresDataSource.name + ", "
                }
            }
            return str.substring(0, str.length - 2)
        }
    }


}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context






