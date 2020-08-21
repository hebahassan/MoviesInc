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

class MovieDetailsActivity: BaseActivity<MovieDetailsStates>(R.layout.activity_movie_details) {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: MovieDetailsViewModel
    private val genreList = ArrayList<String>()

    override fun inject() {
        viewModel = ViewModelProvider(this, providerFactory)[MovieDetailsViewModel::class.java]
    }

    override fun init() {
        viewModel.movieDetailsState.observe(this, Observer {
            render(it)
        })

        viewModel.getMovieDetails(intent.getIntExtra(Constant.Extras.MOVIE_ID, 0))
    }

    override fun render(state: MovieDetailsStates) {
        when(state) {
            is MovieDetailsStates.LoadingDetails -> renderLoading()

            is MovieDetailsStates.SuccessDetails -> renderSuccess(state.data)

            is MovieDetailsStates.ErrorDetails -> renderError(state.error)
        }
    }

    private fun renderLoading() {
        progressBar.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun renderSuccess(data: Pair<MovieDetailsModel, MovieCreditsModel>) {
        progressBar.visibility = View.GONE

        data.first.genres.forEach { genreList.add(it.name) }

        requestManager.load("${viewModel.getPosterPath()}${data.first.posterPath}").into(moviePoster)
        titleTV.text = data.first.title
        overviewTV.text = data.first.overview
        ratingTV.text = "${data.first.voteAverage} / 10"
        yearTV.text = data.first.releaseDate.split("-")[0]
        genreTV.text = genreList.joinToString(", ")
    }

    private fun renderError(error: String) {
        progressBar.visibility = View.GONE
        toast(error)
    }
}