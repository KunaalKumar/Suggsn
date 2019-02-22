package com.kunaalkumar.suggsn.results_components

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.kunaalkumar.suggsn.glide_API.GlideApp
import com.kunaalkumar.suggsn.tmdb.MOVIE_MEDIA_TYPE
import com.kunaalkumar.suggsn.tmdb.TMDbItem
import kotlinx.android.synthetic.main.result_list_item.view.*

@Suppress("DEPRECATION")
class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG: String = "ResultViewHolder"
    lateinit var palette: Palette

    fun bindView(data: TMDbItem, mediaType: String?) {

        // Reset view properties to avoid errors
        itemView.poster.setImageDrawable(null)
        itemView.backdrop.setImageDrawable(null)
        itemView.name.setTextColor(Color.WHITE)

        if (mediaType != null) {
            if (mediaType == MOVIE_MEDIA_TYPE)
                itemView.name.text = data.title
            else
                itemView.name.text = data.name
        } else if (data.media_type == MOVIE_MEDIA_TYPE)
            itemView.name.text = data.title
        else
            itemView.name.text = data.name

        if (data.getPoster() != null)
            GlideApp.with(itemView)
                .asBitmap()
                .load(data.getPoster().toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        palette = Palette.from(resource).generate()
                        itemView.poster.setImageBitmap(resource)
                        itemView.name.setTextColor(palette.getLightVibrantColor(Color.WHITE))
                    }
                })

        if (data.getBackdrop() != null)
            GlideApp.with(itemView)
                .load(data.getBackdrop().toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(itemView.backdrop)

//        itemView.parent_card_view.setOnClickListener {
//            itemView.context.startActivity(Intent(itemView.context, DetailsActivity::class.java))
//        }
    }
}