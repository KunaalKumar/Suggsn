package com.kunaalkumar.suggsn.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.kunaalkumar.suggsn.R

class CircularRatingView : View {
    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {

    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(ContextCompat.getColor(context, R.color.colorAccent))

        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bgPaint.color = Color.BLACK

        canvas?.drawCircle(width / 2f, height / 2f, width * 0.50f, bgPaint)
    }
}