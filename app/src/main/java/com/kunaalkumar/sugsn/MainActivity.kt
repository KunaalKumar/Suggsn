package com.kunaalkumar.sugsn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.view_model.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get screen size and convert to pixels from dpi for poster images
//        val displayMetric = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetric)
//        val width = (displayMetric.widthPixels * 0.5).roundToInt()
//        val height = (width * 1.5).roundToInt()

        top_movies_rv.layoutManager = LinearLayoutManager(this)
        top_movies_rv.adapter = viewModel.adapter
        top_movies_rv.setItemViewCacheSize(20)
        top_movies_rv.hasFixedSize()

        top_movies_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this.adapter
        }

        viewModel.getTopMovies()

        // Scroll additional items once user reaches end of list
        top_movies_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextTenItems()
                }
            }
        })
    }


}
