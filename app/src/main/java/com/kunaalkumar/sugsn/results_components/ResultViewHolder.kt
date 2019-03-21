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
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.PERSON_MEDIA_TYPE
import com.kunaalkumar.sugsn.tmdb.TMDbItem
import kotlinx.android.synthetic.main.result_list_item.view.*

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG: String = "ResultViewHolder"

    fun bindView(data: TMDbItem, mediaType: String?) {

        itemView.poster.layoutParams.width = TmdbRepository.WIDTH
        itemView.poster.layoutParams.height = TmdbRepository.HEIGHT

        if (mediaType.equals(PERSON_MEDIA_TYPE)) {
            if (data.getProfilePath() != null) {
                loadImage(data.getProfilePath().toString())
            }
        } else {
            if (data.getPoster() != null)
                loadImage(data.getPoster().toString())
        }

        // Show item title/name on long press
        itemView.poster.setOnLongClickListener {
            var name: String? = data.name.toString()
            when (mediaType) {
                "movie" -> name = data.title.toString()
            }
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

            //TODO: Implement TV and People

            //WARNING: ONLY FOR MOVIES
            // Poster/Backdrop url, movie id, and name
            intent.putExtra(MovieDetailsActivity.POSTER, data.getPoster().toString())
            intent.putExtra(MovieDetailsActivity.BACKDROP, data.getBackdrop().toString())
            intent.putExtra(MovieDetailsActivity.MOVIE_ID, data.id.toString())
            intent.putExtra(MovieDetailsActivity.ITEM_NAME, data.title.toString())

            itemView.context.startActivity(intent, options.toBundle())
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