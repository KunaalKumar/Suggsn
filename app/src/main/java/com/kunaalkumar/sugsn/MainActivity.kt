package com.kunaalkumar.sugsn

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.topRatedMoviesList.observe(this, Observer<String> {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_dest -> {
                    //openFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.movies_dest -> {
                    //openFragment(MoviesFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.shows_dest -> {
                    //openFragment(ShowsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.people_dest -> {
                    //openFragment(PeopleFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).disallowAddToBackStack()
        transaction.commit()
    }
}
