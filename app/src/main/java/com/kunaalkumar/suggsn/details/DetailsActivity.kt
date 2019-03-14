package com.kunaalkumar.suggsn.details

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.glide_API.GlideApp
import com.kunaalkumar.suggsn.view_model.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        // Intent extra tags
        const val ITEM_NAME = "ITEM_NAME"
        const val BACKDROP = "BACKDROP"
        const val POSTER = "POSTER"
        const val MOVIE_ID = "MOVIE_ID"
    }

    private var detailColor: Int = 0
    private var textColor: Int = 0

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
        textColor = ContextCompat.getColor(
            applicationContext,
            android.R.color.black
        )

        setSupportActionBar(toolbar)

        collapsing_toolbar.title = intent.getStringExtra(ITEM_NAME)

        // Load image and enable colors
        loadImage(intent.getStringExtra(BACKDROP), movie_backdrop)
        loadImageAndSetDetailColor(intent.getStringExtra(POSTER), movie_poster)

        viewModel.getMovieDetails(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer {})
        viewModel.getMovieData().observe(this, Observer {
            // TODO: Populate UI with data
        })
    }

    // Load image into given imageView and extract detail color using palette
    private fun loadImageAndSetDetailColor(image: String, imageView: ImageView) {
        GlideApp.with(this)
            .asBitmap()
            .load(image)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
//                    itemView.progress_bar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
//                    itemView.progress_bar.visibility = View.GONE
                    Palette.from(resource).generate { palette: Palette? ->
                        detailColor = palette!!.getVibrantColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.darkGray
                            )
                        )

                        // Same as detailed color except that it defaults to white
                        val expandedColor = palette.getVibrantColor(
                            ContextCompat.getColor(
                                applicationContext,
                                android.R.color.white
                            )
                        )

                        // Set colors
                        collapsing_toolbar.setCollapsedTitleTextColor(detailColor)
                        collapsing_toolbar.setExpandedTitleColor(expandedColor)
                    }
                    return false
                }
            })
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    private fun loadImage(image: String, imageView: ImageView) {
        GlideApp.with(this)
            .load(image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}
