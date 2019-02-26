package com.kunaalkumar.suggsn.results_components

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kunaalkumar.suggsn.glide_API.GlideApp
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.PERSON_MEDIA_TYPE
import com.kunaalkumar.suggsn.tmdb.TMDbItem
import kotlinx.android.synthetic.main.result_list_item.view.*

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG: String = "ResultViewHolder"

    fun bindView(data: TMDbItem, mediaType: String?) {

//        itemView.poster.layoutParams.width = 200 * itemView.context.resources.displayMetrics.density.roundToInt()
        itemView.poster.layoutParams.width = TmdbRepository.WIDTH
        itemView.poster.layoutParams.height = TmdbRepository.HEIGHT
        if (mediaType.equals(PERSON_MEDIA_TYPE)) {
            if (data.getProfilePath() != null) {
                GlideApp.with(itemView)
                    .load(data.getProfilePath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.poster)
            }
        } else {
            if (data.getPoster() != null)
                GlideApp.with(itemView)
                    .load(data.getPoster())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.poster)
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
        }
    }
}