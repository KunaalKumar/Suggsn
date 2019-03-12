package com.kunaalkumar.suggsn.details

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.glide_API.GlideApp
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {


    companion object {
        //TODO: Implement poster and fix shared element transition linking
        const val ITEM_NAME = "ITEM_NAME"
        const val BACKDROP = "BACKDROP"
        const val MOVIE_ID = "MOVIE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        collapsing_toolbar.title = intent.getStringExtra(ITEM_NAME)
        collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
        collapsing_toolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.black))

        //TODO: Make viewmodel to get movie/item data via id passed

        loadImage(intent.getStringExtra(BACKDROP))
    }

    private fun loadImage(image: String) {
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
                        val collapsedColor = palette!!.getVibrantColor(
                            ContextCompat.getColor(
                                applicationContext,
                                android.R.color.black
                            )
                        )
                        collapsing_toolbar.setCollapsedTitleTextColor(collapsedColor)
                    }
                    return false
                }
            })
            .thumbnail(0.01f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(item_poster)
    }
}
