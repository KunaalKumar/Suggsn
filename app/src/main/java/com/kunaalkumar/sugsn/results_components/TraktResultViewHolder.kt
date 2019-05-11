package com.kunaalkumar.sugsn.results_components

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kunaalkumar.sugsn.details.MovieDetailsActivity
import com.kunaalkumar.sugsn.glide_API.GlideApp
import com.kunaalkumar.sugsn.tmdb.TMDbMovieDetails
import kotlinx.android.synthetic.main.result_list_item.view.*

class TraktResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val TAG: String = "TraktResultViewHolder"

    fun bindView(data: Any?) {

        if (data is TMDbMovieDetails) {
            loadImage(data.getPoster() ?: "http://noodleblvd.com/wp-content/uploads/2016/10/No-Image-Available.jpg")

            // Show item title/name on long press
            itemView.poster.setOnLongClickListener {
                val name: String? = data.original_title
                Toast.makeText(it.context, name, Toast.LENGTH_SHORT).show()
                return@setOnLongClickListener true
            }

            itemView.poster.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailsActivity::class.java)

                // Shared element transition elements
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity, itemView.poster,
                    ViewCompat.getTransitionName(itemView.poster)!!
                )

                // Poster/Backdrop url, movie id, and name
                intent.putExtra(MovieDetailsActivity.ITEM_DATA, data)
                itemView.context.startActivity(intent, options.toBundle())
            }
        }
    }

    private fun loadImage(image: String) {
        GlideApp.with(itemView)
            .load(image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.progress_bar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.progress_bar.visibility = View.GONE
                    return false
                }
            })
            .thumbnail(0.05f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(itemView.poster)
    }
}