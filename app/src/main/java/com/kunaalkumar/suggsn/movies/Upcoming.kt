package com.kunaalkumar.suggsn.movies


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
import com.kunaalkumar.suggsn.repositories.TmdbRepository.MOVIES_UPCOMING
import com.kunaalkumar.suggsn.results_components.ResultsAdapter
import com.kunaalkumar.suggsn.tmdb.MOVIE_MEDIA_TYPE
import com.kunaalkumar.suggsn.view_model.MoviesViewModel
import kotlinx.android.synthetic.main.activity_search.*

class Upcoming : Fragment() {

    val TAG: String = "Suggsn@Upcoming"

    private lateinit var viewModel: MoviesViewModel
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
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        initRecyclerView()

        viewModel.getMovies(MOVIES_UPCOMING).observe(this, Observer { })

        viewModel.getUpcomingList().observe(this, Observer {
            if (it != null)
                viewAdapter.setResults(it)
        })
    }

    private fun initRecyclerView() {
        viewManager = GridLayoutManager(context, 2)
        viewAdapter = ResultsAdapter(MOVIE_MEDIA_TYPE)
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
                    viewModel.nextPage(MOVIES_UPCOMING)
                }
            }
        })
        Log.i(TAG, "initRecyclerView: initialized recycler view")
    }
}
