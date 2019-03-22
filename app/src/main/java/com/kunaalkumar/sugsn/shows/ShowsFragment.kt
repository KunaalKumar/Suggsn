package com.kunaalkumar.sugsn.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TVFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TVFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShowsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shows, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(Popular(), "Popular")
        adapter.addFragment(TopRated(), "Top Rated")
        adapter.addFragment(OnAir(), "On TV")
        adapter.addFragment(AiringToday(), "Airing Today")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}
