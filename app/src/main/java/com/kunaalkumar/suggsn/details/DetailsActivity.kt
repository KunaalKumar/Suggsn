package com.kunaalkumar.suggsn.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.glide_API.GlideApp
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        GlideApp.with(this)
            .load("") // Load image here
            .into(item_poster)
    }
}
