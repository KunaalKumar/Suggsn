package com.kunaalkumar.suggsn.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.results_components.ResultsAdapter
import com.kunaalkumar.suggsn.tmdb.MOVIE_MEDIA_TYPE
import com.kunaalkumar.suggsn.view_model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home_movies.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeMovies.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeMovies.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeMovies : Fragment() {

    val TAG: String = "Suggsn@HomeMovies"

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewAdapter: ResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_movies, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        initRecyclerView()

        viewModel.getDiscoverMovies().observe(this, Observer {})

        viewModel.getMovieList().observe(this, Observer {
            if (it != null) {
                viewAdapter.setResults(it)
            }
        })
    }

    private fun initRecyclerView() {
        viewManager = LinearLayoutManager(context)
        viewAdapter = ResultsAdapter(MOVIE_MEDIA_TYPE)
        recycler_view.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(40)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
