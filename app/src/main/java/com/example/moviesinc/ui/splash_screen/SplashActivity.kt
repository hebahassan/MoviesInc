package com.example.moviesinc.ui.splash_screen

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesinc.R
import com.example.moviesinc.ui.movies_screen.MoviesActivity
import org.jetbrains.anko.startActivity

class SplashActivity: AppCompatActivity() {

    //TODO: check for session id to detect login state
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity<MoviesActivity>()
            finish()
        }, 5000)
    }
}