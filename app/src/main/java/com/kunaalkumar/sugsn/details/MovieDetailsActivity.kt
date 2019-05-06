package com.kunaalkumar.sugsn.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.glide_API.GlideApp
import com.kunaalkumar.sugsn.view_model.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.details_bar.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        // Intent extra tags
        const val ITEM_NAME = "ITEM_NAME"
        const val BACKDROP = "BACKDROP"
        const val POSTER = "POSTER"
        const val MOVIE_ID = "MOVIE_ID"
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        item_title.text = intent.getStringExtra(ITEM_NAME)

        GlideApp.with(this)
            .load(intent.getStringExtra(POSTER))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(item_poster)

        GlideApp.with(this)
            .load(intent.getStringExtra(BACKDROP))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(item_backdrop)

        viewModel.loadDetails(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer {
            rating_text.text = it.vote_average.toString()
            runtime_text.text = it.runtime.toString()
        })
    }
}
