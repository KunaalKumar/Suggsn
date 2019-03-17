package com.kunaalkumar.suggsn

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_nav_bar.setupWithNavController(navController)
        // Get screen size and convert to pixels from dpi for poster images

        val displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        TmdbRepository.WIDTH = (displayMetric.widthPixels * 0.5).roundToInt()
        TmdbRepository.HEIGHT = (TmdbRepository.WIDTH * 1.5).roundToInt()

//        bottom_nav_bar.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.home_dest ->
//                    navController.navigate(R.id.home_dest)
//                R.id.movies_dest ->
//                    navController.navigate(R.id.movies_dest)
//                R.id.shows_dest ->
//                    navController.navigate(R.id.shows_dest)
//                R.id.people_dest ->
//                    navController.navigate(R.id.people_dest)
//            }
//            return@setOnNavigationItemSelectedListener true
//        }
    }
}
