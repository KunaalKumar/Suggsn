package com.kunaalkumar.sugsn.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment() {

    private lateinit var tabAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tabAdapter = ViewPagerAdapter(childFragmentManager)
        tabAdapter.addFragment(Movies(), "Movies")
//        tabAdapter.addFragment(Shows(viewModel), "TV Shows")
        viewPager.adapter = tabAdapter
        tabs.setupWithViewPager(viewPager)
    }
}
