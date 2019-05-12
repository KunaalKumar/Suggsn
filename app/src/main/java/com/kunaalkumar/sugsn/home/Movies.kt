package com.kunaalkumar.sugsn.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.results_components.TraktResultAdapter
import com.kunaalkumar.sugsn.view_model.HomeViewModel
import kotlinx.android.synthetic.main.fragments_recylcer_view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Movies.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Movies.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Movies : Fragment() {

    val TAG: String = "Sugsn@Movies"

    private lateinit var viewAdapter: TraktResultAdapter
    private lateinit var viewManager: GridLayoutManager

    private val viewModel: HomeViewModel by lazy {
        activity?.run {
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragments_recylcer_view, container, false)
    }

    private fun initRecyclerView() {
        viewManager = GridLayoutManager(activity, 2)
        viewAdapter = TraktResultAdapter(viewLifecycleOwner)
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.nextPage(HomeViewModel.MOVIES)
                }
            }
        })
        Log.i(TAG, "initRecyclerView: initialized recycler view")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()

        viewModel.getTrending(HomeViewModel.MOVIES).observe(viewLifecycleOwner, Observer {
            // Set current and last pages
            viewModel.setLastPage(HomeViewModel.MOVIES, it.totalPages)
            viewAdapter.addResults(it.response)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view.adapter = null
        recycler_view.layoutManager = null
    }
}
