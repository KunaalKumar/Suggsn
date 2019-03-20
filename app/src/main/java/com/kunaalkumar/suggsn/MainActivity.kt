package com.kunaalkumar.suggsn

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kunaalkumar.suggsn.home.HomeFragment
import com.kunaalkumar.suggsn.movies.MoviesFragment
import com.kunaalkumar.suggsn.people.PeopleFragment
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.shows.ShowsFragment
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

        val homeFragment = HomeFragment()
        val moviesFragment = MoviesFragment()
        val showsFragment = ShowsFragment()
        val peopleFragment = PeopleFragment()
        openFragment(homeFragment)

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_dest -> {
                    openFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.movies_dest -> {
                    openFragment(moviesFragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.shows_dest -> {
                    openFragment(showsFragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.people_dest -> {
                    openFragment(peopleFragment)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
