package com.kunaalkumar.sugsn

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kunaalkumar.sugsn.home.HomeFragment
import com.kunaalkumar.sugsn.movies.MoviesFragment
import com.kunaalkumar.sugsn.people.PeopleFragment
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.shows.ShowsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get screen size and convert to pixels from dpi for poster images
        val displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        TmdbRepository.WIDTH = (displayMetric.widthPixels * 0.5).roundToInt()
        TmdbRepository.HEIGHT = (TmdbRepository.WIDTH * 1.5).roundToInt()

        openFragment(HomeFragment())

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_dest -> {
                    openFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.movies_dest -> {
                    openFragment(MoviesFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.shows_dest -> {
                    openFragment(ShowsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.people_dest -> {
                    openFragment(PeopleFragment())
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
