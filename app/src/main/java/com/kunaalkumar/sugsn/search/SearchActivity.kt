package com.kunaalkumar.sugsn.search

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.glide_API.GlideApp
import com.kunaalkumar.sugsn.results_components.TmdbResultsAdapter
import com.kunaalkumar.sugsn.view_model.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_main.*

/**
 *  Responsible for the following:
 * 1) Loading screen
 * 2) Home screen
 * 3) Results screen
 */
class SearchActivity : AppCompatActivity() {

    private val TAG = "Sugsn@ SearchActivity"
    private lateinit var viewModel: SearchViewModel

    private lateinit var viewAdapterTmdb: TmdbResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        // Animate loading text
        ObjectAnimator.ofFloat(loading_text_view, "alpha", 0.25f, 1f)
            .apply {
                duration = 2000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                start()
            }

        initRecyclerView()

        search_button.setOnClickListener {
            if (search_edit_text.text!!.isEmpty()) { // Check for valid search term
                search_edit_text.error = "Can't be empty"
                return@setOnClickListener
            }
            transitionToResultsLayout()

            viewModel.searchFor(search_edit_text.text.toString(), 1, false)
                .observe(this, Observer {})

            viewModel.getResults()
                .observe(this, Observer {
                    android.util.Log.d(TAG, "onCreate: searchFor changed")
                    if (it != null) {
                        viewAdapterTmdb.setResults(it)
                    }
                })
        }

        // Switch to activity_search_search
        viewModel.getConfigStatus().observe(this, Observer {
            android.util.Log.d(TAG, "onCreate: Config status change")
            transitionToSearchLayout()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        GlideApp.get(this).clearMemory()
        GlideApp.get(this).clearDiskCache()
    }

    // Transition to activity_search_search
    private fun transitionToSearchLayout() {
        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(this, R.layout.activity_search_search)
        TransitionManager.beginDelayedTransition(activity_main_layout)
        constraintSet1.applyTo(activity_main_layout)

        // Load backdrop image
        viewModel.getBackdropImageUrl().observe(this, Observer {
            Log.d(TAG, "Got image: $it")
            GlideApp.with(this)
                .load(it)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(background)
        })
    }

    // Transition to activity_search_results
    private fun transitionToResultsLayout() {
        search_edit_text.clearFocus()

        val constraintSet = ConstraintSet()
        val constraintSet2 = ConstraintSet() // Transition for search layout
        constraintSet.clone(this, R.layout.activity_search_results)
        constraintSet2.clone(this, R.layout.search_results)
        TransitionManager.beginDelayedTransition(activity_main_layout)
        constraintSet2.applyTo(include as ConstraintLayout)
        constraintSet.applyTo(activity_main_layout)

        search_edit_text.hint = null
    }

    // Initialize recycler view
    private fun initRecyclerView() {
        android.util.Log.d(TAG, "initRecyclerView: Initializing recycler view")
        viewManager = LinearLayoutManager(this)
        viewAdapterTmdb = TmdbResultsAdapter(null)
        recycler_view.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(40) // Cache 40 items to ensure minimum loading times
            layoutManager = viewManager
            adapter = viewAdapterTmdb
        }

        // Detect when recycler view is at bottom
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.nextPage()
                }
            }
        })
    }
}
