package com.kunaalkumar.suggsn.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.glide_API.GlideApp
import com.kunaalkumar.suggsn.view_model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "Suggsn@ MainActivity"
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // Animate loading text
        ObjectAnimator.ofFloat(loading_text_view, "alpha", 0f, 1f)
            .apply {
                duration = 2500
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                start()
            }

        viewModel.getBackdropImageUrl().observe(this, Observer<String> {

            Log.d(TAG, "Got image: $it")
            GlideApp.with(this)
                .load(it)
                .centerCrop()
                .into(background)
        })
    }

}
