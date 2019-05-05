package com.kunaalkumar.sugsn.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.databinding.ActivityDetailsBinding
import com.kunaalkumar.sugsn.view_model.DetailsViewModel

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

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.loadDetails(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer { })
//
//        // Default colors
//        detailColor = ContextCompat.getColor(
//            applicationContext,
//            R.color.colorPrimary
//        )
//        expandedTitleColor = ContextCompat.getColor(
//            applicationContext,
//            android.R.color.black
//        )
//
//        GlideApp.with(this)
//            .load(intent.getStringExtra(POSTER))
//            .thumbnail(0.05f)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .into(item_poster)
//
//        GlideApp.with(this)
//            .load(intent.getStringExtra(BACKDROP))
//            .into(item_backdrop)
//
//        viewModel.getMovieVideos(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer { })
//        viewModel.getMovieVideos().observe(this, Observer {
//            //TODO: Link Video by setting on click listener
////            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=${it.results[0].key}")))
//        })
    }
}
