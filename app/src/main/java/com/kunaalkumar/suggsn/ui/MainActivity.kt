package com.kunaalkumar.suggsn.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kunaalkumar.suggsn.R

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    companion object {
        var BASE_IMAGE_URL: String = "http://image.tmdb.org/t/p/"
        var BASE_POSTER_SIZE: String = "original"
        var BASE_BACKDROP_SIZE: String = "original"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
