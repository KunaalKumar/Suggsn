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
            itemView.name.text = data.title
        else
            itemView.name.text = data.name


        if (data.getPoster() != null)
            itemView.poster.displayImage(data.getPoster().toString())

        if (data.getBackdrop() != null)
            itemView.backdrop.displayImage(data.getBackdrop().toString())
    }
}