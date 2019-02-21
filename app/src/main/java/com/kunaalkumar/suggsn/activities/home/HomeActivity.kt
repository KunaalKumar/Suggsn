package com.kunaalkumar.suggsn.activities.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kunaalkumar.suggsn.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home ->
                    Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
                R.id.nav_movies ->
                    Toast.makeText(this, "Movie", Toast.LENGTH_SHORT).show()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
