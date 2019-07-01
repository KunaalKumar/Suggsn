package com.kunaalkumar.sugsn

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunaalkumar.sugsn.view_model.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get screen size and convert to pixels from dpi for poster images
        val displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        val width = (displayMetric.widthPixels * 0.5).roundToInt()
        val height = (width * 1.5).roundToInt()

        top_movies_rv.layoutManager = LinearLayoutManager(this)

        val adapter = RecyclerViewAdapter(this)
        top_movies_rv.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.topRatedMoviesList.observe(this, Observer { movieList ->
            movieList.forEachIndexed { index, movie ->
                adapter.addItem(ListItemModel(movie.title, movie.poster))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
