package com.example.moviesinc.ui.movies_screen

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesinc.R
import com.example.moviesinc.app.ViewModelProviderFactory
import com.example.moviesinc.model.MovieResult
import com.example.moviesinc.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject

//TODO: refresh list
class MoviesActivity: BaseActivity<MoviesStates>(R.layout.activity_movies){

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    @Inject
    lateinit var adapter: MoviesAdapter

    private lateinit var viewModel: MoviesViewModel

    override fun inject() {
        viewModel = ViewModelProvider(this, providerFactory)[MoviesViewModel::class.java]
    }

    override fun init() {
        viewModel.moviesState.observe(this, Observer {
            render(it)
        })
    }

    override fun render(state: MoviesStates) {
        when(state) {
            is MoviesStates.LoadingState -> renderLoadingState()

            is MoviesStates.SuccessState -> renderSuccessState(state.data)

            is MoviesStates.ErrorState -> renderErrorState(state.error)
        }
    }

    private fun renderLoadingState() {
        progressBar.visibility = View.VISIBLE
        errorTV.visibility = View.GONE
    }

    private fun renderSuccessState(data: List<MovieResult>) {
        progressBar.visibility = View.GONE
        errorTV.visibility = View.GONE
        moviesRV.adapter = adapter
        adapter.updateList(data)
    }

    @SuppressLint("SetTextI18n")
    private fun renderErrorState(error: String) {
        progressBar.visibility = View.GONE
        errorTV.visibility = View.VISIBLE
        errorTV.text = "$error\nPlease refresh the page"
    }
}