package com.kunaalkumar.suggsn.home

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
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = TabsPagerAdapter(activity!!.supportFragmentManager)
        adapter.addFragment(Movies(), "Movies")
        adapter.addFragment(Shows(), "TV Shows")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

}
