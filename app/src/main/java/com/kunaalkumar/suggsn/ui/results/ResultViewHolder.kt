package com.kunaalkumar.suggsn.ui.results

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.suggsn.tmdb.MOVIE_MEDIA_TYPE
import com.kunaalkumar.suggsn.tmdb.TMDbItem
import kotlinx.android.synthetic.main.result_list_item.view.*

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG: String = "ResultViewHolder"

    fun bindView(data: TMDbItem) {
        if (data.media_type == MOVIE_MEDIA_TYPE)
            itemView.result_name.text = data.title
        else
            itemView.result_name.text = data.name

        itemView.result_description.text = data.overview

        if (data.getPoster() != null)
            itemView.result_image_view.displayImage(data.getPoster().toString())
    }
}