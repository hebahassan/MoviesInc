package com.example.moviesinc.ui.splash_screen

import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesinc.R
import com.example.moviesinc.app.ViewModelProviderFactory
import com.example.moviesinc.ui.base.BaseActivity
import com.example.moviesinc.ui.movies_screen.MoviesActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

class SplashActivity: BaseActivity<SplashStates>(R.layout.activity_splash) {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: SplashViewModel

    override fun inject() {
        viewModel = ViewModelProvider(this, providerFactory)[SplashViewModel::class.java]
    }

    override fun init() {
        viewModel.splashState.observe(this, Observer {
            render(it)
        })
    }

    override fun render(state: SplashStates) {
        when(state) {
            is SplashStates.ExistedList -> renderExistedData()

            is SplashStates.Success -> renderSuccess()

            is SplashStates.Error -> renderError(state.error)
        }
    }

    private fun renderExistedData() {
        Handler().postDelayed({
            startActivity<MoviesActivity>()
            finish()
        }, 3000)
    }

    private fun renderSuccess() {
        startActivity<MoviesActivity>()
        finish()
    }

    private fun renderError(error: String) {
        startActivity<MoviesActivity>()
        finish()
    }
}