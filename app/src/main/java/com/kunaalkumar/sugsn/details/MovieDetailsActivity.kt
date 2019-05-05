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

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.loadDetails(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer { })
    }
}
