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

        tabAdapter.addFragment(getFragmentWithVM(ChildMoviesFragment.TOP_MOVIE), "Top Rated")
        tabAdapter.addFragment(getFragmentWithVM(ChildMoviesFragment.POPULAR_MOVIE), "Popular")

        viewPager.adapter = tabAdapter
        tabs.setupWithViewPager(viewPager)
    }

    // Returns ChildMoviesFragment with given viewModel type in args
    private fun getFragmentWithVM(viewModelType: String): Fragment {
        val fragment = ChildMoviesFragment()
        val args = Bundle()
        args.putString("viewModelType", viewModelType)
        fragment.arguments = args
        return fragment
    }
}