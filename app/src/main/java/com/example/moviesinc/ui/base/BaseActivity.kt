package com.example.moviesinc.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T>(@LayoutRes private val layoutId: Int): DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        inject()
        init()
    }

    abstract fun inject()
    abstract fun init()
    abstract fun render(state: T)
}