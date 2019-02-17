package com.kunaalkumar.suggsn.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.glide_API.GlideApp
import com.kunaalkumar.suggsn.view_model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "Suggsn@ MainActivity"
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // Animate loading text
        ObjectAnimator.ofFloat(loading_text_view, "alpha", 0.25f, 1f)
            .apply {
                duration = 2000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                start()
            }

        viewModel.getBackdropImageUrl().observe(this, Observer {

            Log.d(TAG, "Got image: $it")
            GlideApp.with(this)
                .load(it)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(background)
        })

        // Switch to activity_main_search
        viewModel.getConfigStatus().observe(this, Observer {
            android.util.Log.d(TAG, "onCreate: Config status change")
            transitionToSearchLayout()
        })

        search_button.setOnClickListener {
            transitionToResultsLayout()
        }

    }

    // Transition to activity_main_search
    private fun transitionToSearchLayout() {
        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(this, R.layout.activity_main_search)
        TransitionManager.beginDelayedTransition(activity_main_layout)
        constraintSet1.applyTo(activity_main_layout)
    }

    // Transition to activity_main_results
    private fun transitionToResultsLayout() {
        val constraintSet = ConstraintSet()
        val constraintSet2 = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_main_results)
        constraintSet2.clone(this, R.layout.search_results)
        TransitionManager.beginDelayedTransition(activity_main_layout)
        constraintSet.applyTo(activity_main_layout)
        constraintSet2.applyTo(edit_text_relative_layout as ConstraintLayout)
        background.visibility = View.GONE
    }

}
