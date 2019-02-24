package com.kunaalkumar.suggsn.home

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
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.DISCOVER_TV
import com.kunaalkumar.suggsn.results_components.ResultsAdapter
import com.kunaalkumar.suggsn.tmdb.TV_MEDIA_TYPE
import com.kunaalkumar.suggsn.view_model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home_movies.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeTVShows.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeTVShows.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeTVShows : Fragment() {

    val TAG: String = "Suggsn@HomeTVShows"

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewAdapter: ResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_tv_shows, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        initRecyclerView()

        viewModel.getDiscover(DISCOVER_TV).observe(this, Observer {})

        viewModel.getTVList().observe(this, Observer {
            if (it != null)
                viewAdapter.setResults(it)
        })
    }

    private fun initRecyclerView() {
        viewManager = GridLayoutManager(context, 2)
        viewAdapter = ResultsAdapter(TV_MEDIA_TYPE)
        recycler_view.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.nextPage(DISCOVER_TV)
                }
            }
        })
        Log.i(TAG, "initRecyclerView: initialized recycler view")
    }
}
