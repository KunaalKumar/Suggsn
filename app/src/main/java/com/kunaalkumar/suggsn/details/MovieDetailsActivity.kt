package com.kunaalkumar.suggsn.details

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
        collapsing_toolbar.title = intent.getStringExtra(ITEM_NAME)
        setSupportActionBar(toolbar)

        // Load image and enable colors
        loadImageAndSetDetailColor(intent.getStringExtra(POSTER), item_poster)

        viewModel.getMovieDetails(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer {})
        viewModel.getMovieData().observe(this, Observer {
            // TODO: Populate UI with data
            item_tagline.text = it.tagline
            item_rating.text = it.vote_average.toString()
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
                        detailColor = palette!!.getDarkVibrantColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.darkGray
                            )
                        )

                        expandedTitleColor = palette.getVibrantColor(
                            ContextCompat.getColor(
                                applicationContext,
                                android.R.color.white
                            )
                        )
                        loadImageAndUpdateColors(intent.getStringExtra(BACKDROP), movie_backdrop)
                    }
                    return false
                }
            })
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    private fun loadImageAndUpdateColors(image: String, imageView: ImageView) {
        GlideApp.with(this)
            .load(image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Update colors
                    collapsing_toolbar.setCollapsedTitleTextColor(detailColor)
                    collapsing_toolbar.setExpandedTitleColor(expandedTitleColor)
                    item_tagline.setTextColor(expandedTitleColor)
                    item_rating.setTextColor(expandedTitleColor)
                    return false
                }
            })
            .into(imageView)
    }
}
