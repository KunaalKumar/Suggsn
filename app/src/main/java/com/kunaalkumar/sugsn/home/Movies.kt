package com.kunaalkumar.sugsn.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.results_components.TraktResultAdapter
import com.kunaalkumar.sugsn.tmdb.TMDbMovieDetails
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
class Movies(val viewModel: HomeViewModel) : Fragment() {

    val TAG: String = "Sugsn@Movies"

    private lateinit var viewAdapter: TraktResultAdapter<TMDbMovieDetails>
    private lateinit var viewManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragments_recylcer_view, container, false)
    }

    private fun initRecyclerView() {
        viewManager = GridLayoutManager(activity, 2)
        viewAdapter = TraktResultAdapter()
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

            // Get image for each result
            it.response.forEach {
                TmdbRepository.getMovieDetails(it.movie.ids.tmdb).observe(viewLifecycleOwner, Observer { movie ->
                    if (movie != null)
                        viewAdapter.addResults(movie)
                })
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view.adapter = null
        recycler_view.layoutManager = null
    }
}
