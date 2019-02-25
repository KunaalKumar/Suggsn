package com.kunaalkumar.suggsn.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.TabsPagerAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = TabsPagerAdapter(activity!!.supportFragmentManager)
        adapter.addFragment(MoviesPopular(), "Popular")
        adapter.addFragment(MoviesTopRated(), "Top Rated")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}