package com.kunaalkumar.sugsn.util

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.glide_API.GlideApp
import kotlin.math.min
import kotlin.math.roundToInt

@BindingAdapter("imgSrc", "parentLayout")
fun setImageSrc(view: AppCompatImageView, poster: String, parentLayout: View) {

    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 15F
    circularProgressDrawable.centerRadius = 50F
    circularProgressDrawable.setColorFilter(
        ContextCompat.getColor(
            view.context,
            R.color.colorPrimary
        ), PorterDuff.Mode.SRC_OVER
    )
    circularProgressDrawable.start()
    view.setImageDrawable(circularProgressDrawable)

    GlideApp.with(view.context)
        .asBitmap()
        .load(poster)
        .apply(RequestOptions().override(view.width, view.height))
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                // Stop progress bar; load poster into view
                circularProgressDrawable.stop()
                view.setImageBitmap(resource)

                // Extract color and set as parent bg
                val palette = Palette.from(resource).generate()
                val colorFrom = (parentLayout.background as ColorDrawable).color
                val colorTo = manipulateColor(
                    palette.getDominantColor(Color.BLACK),
                    0.65f
                )
                // Animate color change
                ObjectAnimator.ofObject(
                    parentLayout,
                    "backgroundColor",
                    ArgbEvaluator(),
                    colorFrom,
                    colorTo
                ).apply {
                    duration = 500
                    start()
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }
        })
}

fun manipulateColor(color: Int, factor: Float): Int {
    val a = Color.alpha(color)
    val r = (Color.red(color) * factor).roundToInt()
    val g = (Color.green(color) * factor).roundToInt()
    val b = (Color.blue(color) * factor).roundToInt()
    return Color.argb(
        a,
        min(r, 255),
        min(g, 255),
        min(b, 255)
    )
}