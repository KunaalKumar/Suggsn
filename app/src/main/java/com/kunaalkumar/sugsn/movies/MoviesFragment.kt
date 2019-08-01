package com.kunaalkumar.sugsn.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    //TODO: Implement viewmodel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tabAdapter = ViewPagerAdapter(childFragmentManager)
        tabAdapter.addFragment(TopRatedMoviesFragment(), "Top Rated")
        viewPager.adapter = tabAdapter
        tabs.setupWithViewPager(viewPager)
    }
}