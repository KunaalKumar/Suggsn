package com.kunaalkumar.suggsn.activities.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunaalkumar.suggsn.R

/**
 *
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PeopleFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PeopleFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PeopleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false)
    }
}
