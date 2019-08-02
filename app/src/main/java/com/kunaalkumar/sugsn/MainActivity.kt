package com.kunaalkumar.sugsn

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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

        if (savedInstanceState != null)
            return

        openFragment(viewModel.moviesFragment)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.movies_dest -> {
                    Toast.makeText(this, "Movies", Toast.LENGTH_LONG).show()
                    openFragment(viewModel.moviesFragment)
                }
                R.id.shows_dest -> {
                    Toast.makeText(this, "Shows", Toast.LENGTH_LONG).show()
                }
                R.id.people_dest -> {
                    Toast.makeText(this, "People", Toast.LENGTH_LONG).show()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
