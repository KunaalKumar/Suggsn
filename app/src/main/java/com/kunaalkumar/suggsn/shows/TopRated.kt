package com.kunaalkumar.suggsn.shows


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
import com.kunaalkumar.suggsn.repositories.TmdbRepository.SHOWS_TOP_RATED
import com.kunaalkumar.suggsn.results_components.ResultsAdapter
import com.kunaalkumar.suggsn.view_model.ShowsViewModel
import kotlinx.android.synthetic.main.activity_search.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TopRated : Fragment() {

    val TAG: String = "Sugssn@Shows/TopRated"

    private lateinit var viewModel: ShowsViewModel
    private lateinit var viewAdapter: ResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragments_recylcer_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowsViewModel::class.java)
        initRecyclerView()

        viewModel.getShows(SHOWS_TOP_RATED).observe(this, Observer { })

        viewModel.getTopRatedList().observe(this, Observer {
            if (it != null)
                viewAdapter.setResults(it)
        })
    }

    private fun initRecyclerView() {
        viewManager = GridLayoutManager(context, 2)
        viewAdapter = ResultsAdapter(SHOWS_TOP_RATED)
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
                    viewModel.nextPage(SHOWS_TOP_RATED)
                }
            }
        })
        Log.i(TAG, "initRecyclerView: initialized recycler view")
    }

}
