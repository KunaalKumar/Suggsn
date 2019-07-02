package com.kunaalkumar.sugsn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.imdb.ImdbListItem
import com.kunaalkumar.sugsn.view_model.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

    val adapter = RecyclerViewAdapter(this)
    lateinit var itemQueue: Queue<ImdbListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get screen size and convert to pixels from dpi for poster images
//        val displayMetric = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetric)
//        val width = (displayMetric.widthPixels * 0.5).roundToInt()
//        val height = (width * 1.5).roundToInt()

        top_movies_rv.layoutManager = LinearLayoutManager(this)
        top_movies_rv.adapter = adapter
        top_movies_rv.setItemViewCacheSize(20)
        top_movies_rv.hasFixedSize()

        top_movies_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this.adapter
//            setItemViewCacheSize(20)
        }

        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.topRatedMoviesList.observe(this, Observer { movieList ->
            itemQueue =
                LinkedList(movieList) // Convert list to queue in order to load items sequentially
            loadNextTenItems()
        })

        // Scroll additional items once user reaches end of list
        top_movies_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    loadNextTenItems()
                }
            }
        })
    }

    // Removes the first ten elements from the queue and adds them to the adapter
    private fun loadNextTenItems() {
        for (i in 1..10) { // Pop first ten items from queue
            adapter.addItem(itemQueue.remove())
        }
    }

}
