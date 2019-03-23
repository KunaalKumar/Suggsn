package com.kunaalkumar.sugsn.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.ViewPagerAdapter
import com.kunaalkumar.sugsn.view_model.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MoviesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MoviesFragment : Fragment() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(Popular(viewModel), "Popular")
        viewPagerAdapter.addFragment(TopRated(viewModel), "Top Rated")
        viewPagerAdapter.addFragment(Upcoming(viewModel), "Upcoming")
        viewPagerAdapter.addFragment(NowPlaying(viewModel), "Now Playing")
        viewPager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }
}
