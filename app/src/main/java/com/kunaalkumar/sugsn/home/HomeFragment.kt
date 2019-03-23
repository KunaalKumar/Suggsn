package com.kunaalkumar.sugsn.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.ViewPagerAdapter
import com.kunaalkumar.sugsn.view_model.HomeViewModel
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
        val viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        tabAdapter = ViewPagerAdapter(childFragmentManager)
        tabAdapter.addFragment(Movies(viewModel), "Movies")
        tabAdapter.addFragment(Shows(viewModel), "TV Shows")
        viewPager.adapter = tabAdapter
        tabs.setupWithViewPager(viewPager)
    }
}
