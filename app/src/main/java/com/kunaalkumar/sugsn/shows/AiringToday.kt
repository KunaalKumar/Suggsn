package com.kunaalkumar.sugsn.shows

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
import com.kunaalkumar.sugsn.results_components.ResultsAdapter
import com.kunaalkumar.sugsn.tmdb.TV_MEDIA_TYPE
import com.kunaalkumar.sugsn.view_model.ShowsViewModel
import kotlinx.android.synthetic.main.activity_search.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AiringToday.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AiringToday.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AiringToday(val viewModel: ShowsViewModel) : Fragment() {
    val TAG: String = "Sugssn@Shows/AiringToda"

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
        initRecyclerView()

        viewModel.getShows(ShowsViewModel.AIRING_TODAY).observe(this, Observer {
            if (it != null) {
                viewModel.setLastPage(ShowsViewModel.AIRING_TODAY, it.total_pages)
                viewAdapter.addResults(ArrayList(it.results))
            }
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
                    viewModel.nextPage(ShowsViewModel.AIRING_TODAY)
                }
            }
        })
        Log.i(TAG, "initRecyclerView: initialized recycler view")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view.adapter = null
        recycler_view.layoutManager = null
    }

}
