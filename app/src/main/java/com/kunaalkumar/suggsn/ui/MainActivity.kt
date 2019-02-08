package com.kunaalkumar.suggsn.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kunaalkumar.suggsn.R

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    companion object {
        lateinit var BASE_IMAGE_URL: String
        lateinit var BASE_POSTER_SIZE: String
        lateinit var BASE_BACKDROP_SIZE: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
