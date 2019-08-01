package com.kunaalkumar.sugsn.movies

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.util.ViewPagerAdapter

class MoviesFragmentViewModel : ViewModel() {
    lateinit var tabAdapter: ViewPagerAdapter

    fun addTabs(childFragmentManager: FragmentManager) {
        if (!this::tabAdapter.isInitialized) {
            tabAdapter = ViewPagerAdapter(childFragmentManager)
            tabAdapter.addFragment(TopRatedMoviesFragment(), "Top Rated")
        }
    }
}