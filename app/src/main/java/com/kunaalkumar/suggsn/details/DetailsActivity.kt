package com.kunaalkumar.suggsn.details

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
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

    private var detailColor: Int = 1
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        detailColor = ContextCompat.getColor(
            applicationContext,
            R.color.colorPrimary
        )

        setSupportActionBar(toolbar)

        collapsing_toolbar.title = intent.getStringExtra(ITEM_NAME)
        collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
        collapsing_toolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.black))

        loadImage(intent.getStringExtra(BACKDROP), item_backdrop)
        loadImageWithPalette(intent.getStringExtra(POSTER), item_poster)

        viewModel.getMovieDetails(intent.getStringExtra(MOVIE_ID).toInt()).observe(this, Observer {})
        viewModel.getMovieData().observe(this, Observer {
            // TODO: Populate UI with data
        })
    }

    private fun loadImageWithPalette(image: String, imageView: ImageView) {
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
                                android.R.color.black
                            )
                        )
                        applyDynamicColor()
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

    private fun applyDynamicColor() {
        collapsing_toolbar.setCollapsedTitleTextColor(detailColor)
        val tintedColor = Color.argb(
            detailColor.alpha / 3,
            detailColor.red,
            detailColor.green,
            detailColor.blue
        )
        item_backdrop.setColorFilter(tintedColor)
    }
}
