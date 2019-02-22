package com.kunaalkumar.suggsn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = findNavController(R.id.nav_host_fragment)

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
