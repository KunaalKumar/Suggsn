package com.kunaalkumar.suggsn.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunaalkumar.suggsn.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeTvShows.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeTvShows.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeTvShows : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_tv_shows, container, false)
    }
}
