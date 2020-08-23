package com.example.moviesinc.ui.movies_screen

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviesinc.R
import com.example.moviesinc.app.ViewModelProviderFactory
import com.example.moviesinc.model.MovieResult
import com.example.moviesinc.ui.base.BaseActivity
import com.example.moviesinc.ui.movie_details_screen.MovieDetailsActivity
import com.example.moviesinc.utils.Constant
import kotlinx.android.synthetic.main.activity_movies.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class MoviesActivity: BaseActivity<MoviesStates>(R.layout.activity_movies),
    SwipeRefreshLayout.OnRefreshListener, MoviesAdapter.OnMovieClickListener{

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    @Inject
    lateinit var adapter: MoviesAdapter

    private lateinit var viewModel: MoviesViewModel

    override fun inject() {
        viewModel = ViewModelProvider(this, providerFactory)[MoviesViewModel::class.java]
    }

    override fun init() {
        swipeRefresh.setOnRefreshListener(this)

        viewModel.moviesState.observe(this, Observer {
            render(it)
        })

        viewModel.getNowPlayingMovies()
    }

    override fun render(state: MoviesStates) {
        when(state) {
            is MoviesStates.LoadingState -> renderLoadingState()

            is MoviesStates.SuccessState -> renderSuccessState(state.data)

            is MoviesStates.ErrorState -> renderErrorState(state.error)
        }
    }

    override fun onRefresh() {
        swipeRefresh.isRefreshing = false
        viewModel.getNowPlayingMovies()
    }

    override fun onMovieClick(movieId: Int) {
        startActivity<MovieDetailsActivity>(Constant.Extras.MOVIE_ID to movieId)
    }

    private fun renderLoadingState() {
        progressBar.visibility = View.VISIBLE
        errorTV.visibility = View.GONE
        moviesRV.visibility = View.GONE
    }

    private fun renderSuccessState(data: List<MovieResult>) {
        progressBar.visibility = View.GONE
        errorTV.visibility = View.GONE
        moviesRV.visibility = View.VISIBLE

        val config = viewModel.getImageConfig()
        adapter.setUrlToImage(config.secureBaseUrl, config.posterSizes.first())
        adapter.setMovieClickListener(this)

        moviesRV.adapter = adapter
        adapter.updateList(data)
    }

    @SuppressLint("SetTextI18n")
    private fun renderErrorState(error: String) {
        progressBar.visibility = View.GONE
        moviesRV.visibility = View.GONE
        errorTV.visibility = View.VISIBLE
        errorTV.text = "$error\nSwipe refresh the page"
    }
}