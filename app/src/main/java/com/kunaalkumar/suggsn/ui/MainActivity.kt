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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.textfield.TextInputLayout
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.glide_API.GlideApp
import com.kunaalkumar.suggsn.results.ResultsAdapter
import com.kunaalkumar.suggsn.view_model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "Suggsn@ MainActivity"
    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

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

        // Load backdrop image
        viewModel.getBackdropImageUrl().observe(this, Observer {
            Log.d(TAG, "Got image: $it")
            GlideApp.with(this)
                .load(it)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(background)
        })

        search_button.setOnClickListener {
            if (search_edit_text.text!!.isEmpty()) { // Check for valid search term
                search_edit_text.error = "Can't be empty"
                return@setOnClickListener
            }
            transitionToResultsLayout()

            viewModel.getSearchResults(search_edit_text.text.toString(), 1, true)

        }

        // Switch to activity_main_search
        viewModel.getConfigStatus().observe(this, Observer {
            android.util.Log.d(TAG, "onCreate: Config status change")
            transitionToSearchLayout()
        })

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
        search_edit_text.clearFocus()

        val constraintSet = ConstraintSet()
        val constraintSet2 = ConstraintSet() // Transition for search layout
        constraintSet.clone(this, R.layout.activity_main_results)
        constraintSet2.clone(this, R.layout.search_results)
        TransitionManager.beginDelayedTransition(activity_main_layout)
        constraintSet2.applyTo(edit_text_relative_layout as ConstraintLayout)
        constraintSet.applyTo(activity_main_layout)

        setupLayoutForSearch()
    }

    // Modify search layout for results view
    private fun setupLayoutForSearch() {
        background.visibility = View.GONE
        search_edit_text.hint = null
        search_edit_text_layout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE)
        search_edit_text_layout.isHintEnabled = false
    }

    private fun initRecyclerView() {
        viewManager = LinearLayoutManager(this)
    }
}
