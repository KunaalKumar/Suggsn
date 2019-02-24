package com.kunaalkumar.suggsn

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = findNavController(R.id.nav_host_fragment)

        // Get screen size and convert to pixels from dpi for poster images
        val displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        val scale = resources.displayMetrics.density
        TmdbRepository.WIDTH = ((displayMetric.xdpi / 2) * scale + 0.5).toInt()
        TmdbRepository.HEIGHT = ((displayMetric.ydpi / 2) * scale + 0.5).toInt()

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home ->
                    navController.navigate(R.id.home_dest)
                R.id.nav_movies ->
                    navController.navigate(R.id.movies_dest)
                R.id.nav_tv ->
                    navController.navigate(R.id.tvs_dest)
                R.id.nav_people ->
                    navController.navigate(R.id.people_dest)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
