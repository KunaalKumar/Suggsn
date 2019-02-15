package com.kunaalkumar.suggsn.ui.results

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.suggsn.glide_API.GlideApp
import com.kunaalkumar.suggsn.tmdb.MOVIE_MEDIA_TYPE
import com.kunaalkumar.suggsn.tmdb.TMDbItem
import kotlinx.android.synthetic.main.result_list_item.view.*

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG: String = "ResultViewHolder"

    fun bindView(data: TMDbItem) {
        if (data.media_type == MOVIE_MEDIA_TYPE)
            itemView.name.text = data.title
        else
            itemView.name.text = data.name

        if (data.getPoster() != null)
            GlideApp.with(itemView)
                .load(data.getPoster().toString())
                .centerCrop()
                .into(itemView.poster)

        if (data.getBackdrop() != null)
            GlideApp.with(itemView)
                .load(data.getBackdrop().toString())
                .centerCrop()
                .into(itemView.backdrop)
    }
}