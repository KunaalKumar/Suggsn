package com.kunaalkumar.sugsn.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.glide_API.GlideApp
import com.kunaalkumar.sugsn.tmdb.TMDbMovieDetails
import com.kunaalkumar.sugsn.view_model.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.details_bar.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        // Intent extra tags
        const val ITEM_DATA = "ITEM_DATA"
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val data = intent.getSerializableExtra(ITEM_DATA) as TMDbMovieDetails

        item_title.text = data.title

        GlideApp.with(this)
            .load(data.getPoster())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(item_poster)

        GlideApp.with(this)
            .load(data.getBackdrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(item_backdrop)

        rating_text.text = data.vote_average.toString()
        runtime_text.text = data.runtime.plus("m")
    }
}
