package com.kunaalkumar.sugsn.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.kunaalkumar.sugsn.glide_API.GlideApp

@BindingAdapter("imgSrc")
fun setImageSrc(view: AppCompatImageView, poster: String) {
    GlideApp.with(view.context)
        .load(poster)
        .into(view)
}