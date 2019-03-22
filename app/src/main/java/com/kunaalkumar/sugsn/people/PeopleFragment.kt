package com.kunaalkumar.sugsn.people

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
import com.kunaalkumar.sugsn.results_components.ResultsAdapter
import com.kunaalkumar.sugsn.tmdb.PERSON_MEDIA_TYPE
import com.kunaalkumar.sugsn.view_model.PeopleViewModel
import kotlinx.android.synthetic.main.activity_search.*

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

    val TAG: String = "Sugsn@PeopleFragment"

    private lateinit var viewModel: PeopleViewModel
    private lateinit var viewAdapter: ResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PeopleViewModel::class.java)
        initRecyclerView()

        viewModel.getPopularPeople().observe(this, Observer {
            if (it != null) {
                viewModel.setLastPage(it.total_pages)
                viewAdapter.addResults(ArrayList(it.results))
            }
        })
    }

    private fun initRecyclerView() {
        viewManager = GridLayoutManager(context, 2)
        viewAdapter = ResultsAdapter(PERSON_MEDIA_TYPE)
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
                    viewModel.nextPage()
                }
            }
        })
        Log.e(TAG, "initRecyclerView: initialized recycler view")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view.adapter = null
        recycler_view.layoutManager = null
    }
}
