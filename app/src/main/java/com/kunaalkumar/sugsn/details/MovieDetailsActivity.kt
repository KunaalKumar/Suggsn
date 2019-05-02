package com.kunaalkumar.sugsn.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.glide_API.GlideApp
import com.kunaalkumar.sugsn.view_model.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        // Intent extra tags
        const val ITEM_NAME = "ITEM_NAME"
        const val BACKDROP = "BACKDROP"
        const val POSTER = "POSTER"
        const val MOVIE_ID = "MOVIE_ID"
    }

    private var detailColor: Int = 0
    private var expandedTitleColor: Int = 0

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)

        // Default colors
        detailColor = ContextCompat.getColor(
            applicationContext,
            R.color.colorPrimary
        )
        expandedTitleColor = ContextCompat.getColor(
            applicationContext,
            android.R.color.black
        )

        item_title.text = intent.getStringExtra(ITEM_NAME)

        GlideApp.with(this)
            .load(intent.getStringExtra(POSTER))
            .into(item_poster)

        GlideApp.with(this)
            .load(intent.getStringExtra(BACKDROP))
            .into(item_backdrop)

        viewModel.getMovieDetails(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer {})
        viewModel.getMovieData().observe(this, Observer {
            item_rating.text = it.vote_average.toString()
        })

        viewModel.getMovieVideos(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer { })
        viewModel.getMovieVideos().observe(this, Observer {
            //TODO: Link Video by setting on click listener
//            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=${it.results[0].key}")))
        })
    }
}
