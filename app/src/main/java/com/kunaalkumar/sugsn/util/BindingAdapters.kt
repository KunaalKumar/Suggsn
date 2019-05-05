package com.kunaalkumar.sugsn.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kunaalkumar.sugsn.glide_API.GlideApp

@BindingAdapter("app:posterSrc")
fun posterSrc(view: AppCompatImageView, src: String?) {
	if (src != null) {
		GlideApp.with(view.context)
			.load(src)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.into(view)
	}
}
