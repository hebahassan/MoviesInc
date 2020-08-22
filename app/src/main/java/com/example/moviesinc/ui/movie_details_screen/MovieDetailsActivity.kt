package com.example.moviesinc.ui.movie_details_screen

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.moviesinc.R
import com.example.moviesinc.app.ViewModelProviderFactory
import com.example.moviesinc.model.MovieCreditsModel
import com.example.moviesinc.model.MovieDetailsModel
import com.example.moviesinc.ui.base.BaseActivity
import com.example.moviesinc.utils.Constant
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MovieDetailsActivity : BaseActivity<MovieDetailsStates>(R.layout.activity_movie_details) {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var adapter: CastAdapter

    lateinit var viewModel: MovieDetailsViewModel
    private var movieId = 0

    override fun inject() {
        viewModel = ViewModelProvider(this, providerFactory)[MovieDetailsViewModel::class.java]
    }

    override fun init() {
        movieId = intent.getIntExtra(Constant.Extras.MOVIE_ID, 0)

        setRatingBar()

        viewModel.movieDetailsState.observe(this, Observer {
            render(it)
        })

        viewModel.getMovieDetails(movieId)
    }

    override fun render(state: MovieDetailsStates) {
        when (state) {
            is FetchDetailsState -> when (state) {
                is FetchDetailsState.Loading -> renderLoading()

                is FetchDetailsState.SuccessDetails -> renderSuccessDetails(state.data)

                is FetchDetailsState.ErrorDetails -> renderError(state.error)
            }

            is RatingState -> when (state) {
                is RatingState.LoadRating -> renderLoading()

                is RatingState.SuccessRating -> renderSuccessRating()

                is RatingState.ErrorRating -> renderErrorRating(state.error)
            }
        }
    }

    private fun renderLoading() {
        progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun renderSuccessDetails(data: Pair<MovieDetailsModel, MovieCreditsModel>) {
        progressBar.visibility = View.GONE

        requestManager.load("${viewModel.getPosterPath()}${data.first.posterPath}")
            .into(moviePoster)
        titleTV.text = data.first.title
        overviewTV.text = data.first.overview
        ratingTV.text = "${data.first.voteAverage} / 10"
        yearTV.text = data.first.releaseDate.split("-")[0]
        genreTV.text = data.first.genres.joinToString(", ") { it.name }

        adapter.setImagePath(viewModel.getProfilePath())
        actorsRV.adapter = adapter
        adapter.updateCastList(data.second.cast)
    }

    private fun renderSuccessRating() {
        progressBar.visibility = View.GONE
        rating.setIsIndicator(true)
    }

    private fun renderError(error: String) {
        progressBar.visibility = View.GONE
        toast(error)
    }

    private fun renderErrorRating(error: String) {
        renderError(error)
        rating.rating = 0f
    }

    private fun setRatingBar() {
        if (rating.rating >= 1)
            rating.setIsIndicator(true)
        else
            rating.setOnRatingBarChangeListener { _, rate, _ ->
                viewModel.rateMovie(movieId, rate.toDouble())
            }
    }
}