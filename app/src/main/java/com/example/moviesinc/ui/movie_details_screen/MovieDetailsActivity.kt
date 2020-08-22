package com.example.moviesinc.ui.movie_details_screen

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.moviesinc.R
import com.example.moviesinc.app.ViewModelProviderFactory
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

        viewModel.searchForMovie(movieId)
        viewModel.getMovieDetails(movieId)

        viewModel.fetchDetailsState.observe(this, Observer {
            render(it)
        })

        viewModel.checkState.observe(this, Observer {
            render(it)
        })

        viewModel.ratingState.observe(this, Observer {
            render(it)
        })
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

            is CheckState -> when(state) {
                is CheckState.ExistedRating -> renderExistedRating(state.ratingValue)

                is CheckState.NoRatingDetected -> renderNoRatingDetected()
            }
        }
    }

    private fun renderLoading() {
        progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun renderSuccessDetails(data: MovieDetailsModel) {
        progressBar.visibility = View.GONE

        requestManager.load("${viewModel.getPosterPath()}${data.posterPath}")
            .into(moviePoster)
        titleTV.text = data.title
        overviewTV.text = data.overview
        ratingTV.text = "${data.voteAverage} / 10"
        yearTV.text = data.releaseDate.split("-")[0]
        genreTV.text = data.genres.joinToString(", ") { it.name }

        adapter.setImagePath(viewModel.getProfilePath())
        actorsRV.adapter = adapter
        adapter.updateCastList(data.credits.cast)
    }

    private fun renderSuccessRating() {
        rating.setIsIndicator(true)
        progressBar.visibility = View.GONE
    }

    private fun renderError(error: String) {
        progressBar.visibility = View.GONE
        toast(error)
    }

    private fun renderErrorRating(error: String) {
        renderError(error)
        rating.rating = 0f
    }

    private fun renderExistedRating(ratingValue: Double) {
        rating.setIsIndicator(true)
        rating.rating = ratingValue.toFloat()
    }

    private fun renderNoRatingDetected() {
        rating.setOnRatingBarChangeListener { _, rate, _ ->
            viewModel.rateMovie(movieId, rate.toDouble())
        }
    }
}