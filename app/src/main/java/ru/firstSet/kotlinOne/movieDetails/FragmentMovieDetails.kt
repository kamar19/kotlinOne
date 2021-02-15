package ru.firstSet.kotlinOne.movieDetails

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.firstSet.kotlinOne.R
import ru.firstSet.kotlinOne.data.Movie
import java.util.*

class FragmentMovieDetails : Fragment() {
    private lateinit var imageViewBack: View
    private lateinit var imageViewShape: View
    private lateinit var fmdPoster: ImageView
    private lateinit var fmdTextViewTeg: TextView
    private lateinit var fmdMovieName: TextView
    private lateinit var fmdSomeId: TextView
    private lateinit var fmdRatingBar: RatingBar
    private lateinit var fmdReview: TextView
    private lateinit var fmdStoryLineContent: TextView
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var yearPlan: Int = 2021
    private var monthPlan: Int = 2
    private var dayPlan: Int = 15
    private var scope = CoroutineScope(Dispatchers.Main)
    val viewModelDetail: ViewModelMovieDetails by viewModel()
    val callbackId: Int = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies_details, container, false)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listRecyclerView = view.findViewById<RecyclerView>(R.id.fmdRecyclerActor)
        imageViewBack = view.findViewById(R.id.fmdImageViewPath)
        imageViewShape = view.findViewById(R.id.fmdImageViewShape)
        fmdTextViewTeg = view.findViewById(R.id.fmdTeg)
        fmdMovieName = view.findViewById(R.id.fmdMovieName)
        fmdSomeId = view.findViewById(R.id.fmdSomeId)
        fmdRatingBar = view.findViewById(R.id.fmdRatingBar)
        fmdPoster = view.findViewById(R.id.fmdPoster)
        fmdReview = view.findViewById(R.id.fmdReview)
        fmdStoryLineContent = view.findViewById(R.id.fmdStoryLineContent)
        progressBar = view.findViewById(R.id.progressBarMovieDetails)
        viewModelDetail.movieDetailStateLiveData.observe(viewLifecycleOwner, this::setState)
        imageViewBack = view.findViewById<View>(R.id.fmdImageViewPath).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        imageViewShape = view.findViewById<View>(R.id.fmdImageViewShape).apply {
            setOnClickListener {
                if (hasPermissions()) {
                    showPickerDialog()
                } else {
                    requestPermissionWithRationale();
                }
            }
            checkPermission(
                callbackId,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
            )
        }
        arguments?.let { viewModelDetail.getMovie(it) }
    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {
            yearPlan = year
            monthPlan = monthOfYear
            dayPlan = dayOfMonth
            updateDateInView()
        }
    }

    private fun showPickerDialog() {
        val datePickerDialog = view?.let {
            DatePickerDialog(
                it.context, dateSetListener, yearPlan, monthPlan, dayPlan
            ).show()
        }
        Log.v("datePicker", "$yearPlan $monthPlan $dayPlan ")
    }

        private fun updateDateInView() {
        Log.v("datePicker2", "$yearPlan $monthPlan $dayPlan ")
        val text:String =  getString(R.string.calendar_event_is_ready) + yearPlan + ", " + monthPlan +", "+ dayPlan
        val toast = Toast.makeText(view?.context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateMovie(movie: Movie) {
        listRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listRecyclerView.adapter = ActorsAdapter(movie.actors)
        fmdMovieName.text = movie.title
        fmdRatingBar.rating = movie.ratings.div(2)
        fmdSomeId.text = movie.adult.toString() + "+"
        scope.launch {
            context?.let {
                Glide
                    .with(it)
                    .load(movie.backdropPicture)
                    .into(fmdPoster)
            }
        }
        fmdMovieName.text = movie.title
        fmdTextViewTeg.text = movie.genres.joinToString(separator = ", ") { it.name }
        fmdReview.text =
            movie.vote_count.toString() + " " + getString(R.string.textViewReview)
        fmdStoryLineContent.text = movie.overview
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    fun setState(state: ViewModelMovieDetails.ViewModelDetailState) =
        when (state) {
            is ViewModelMovieDetails.ViewModelDetailState.Loading ->
                progressBar.visibility = ProgressBar.VISIBLE
            is ViewModelMovieDetails.ViewModelDetailState.Success ->
                updateMovie(state.movie)
            is ViewModelMovieDetails.ViewModelDetailState.Error ->
                errorMessage(state.error)
        }

    fun errorMessage(string: String?) {
        val toast = Toast.makeText(activity, string, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    fun requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),
                Manifest.permission.READ_CALENDAR
            )
        ) {
            activity?.let {
                Snackbar.make(
                    it.findViewById<View>(R.id.fmdBackground),
                    R.string.calendar_permission_is_required_select,
                    Snackbar.LENGTH_LONG
                )
                    .setAction("GRANT") { requestPerms() }
                    .show()
            }
        } else {
            requestPerms()
        }
    }

    private fun requestPerms() {
        val permissions = arrayOf(Manifest.permission.READ_CALENDAR)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        var allowed = true
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> for (res in grantResults) {
                allowed = allowed && res == PackageManager.PERMISSION_GRANTED
            }
            else ->
                allowed = false
        }
        if (allowed) {
            showPickerDialog()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(
                        context,
                        R.string.calendar_permissions_denied,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun hasPermissions(): Boolean {
        var res = 0
        val permissions = arrayOf(Manifest.permission.READ_CALENDAR)
        for (perms in permissions) {
            res = PermissionChecker.checkCallingOrSelfPermission(context as Activity, perms)
            if (res != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun checkPermission(callbackId: Int, vararg permissionsId: String) {
        var permissions = true
        for (p in permissionsId) {
            permissions =
                permissions && context?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        p
                    )
                } == PackageManager.PERMISSION_GRANTED
        }
        if (!permissions) ActivityCompat.requestPermissions(
            context as Activity,
            permissionsId,
            callbackId
        )
    }

    companion object {
        const val KEY_PARSE_DATA = "movieDetails"
        const val PERMISSION_REQUEST_CODE = 152
        var calendarDate: Calendar? = null
        fun newInstance(id: Long) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putLong(KEY_PARSE_DATA, id.toLong())
            }
        }
    }
}

