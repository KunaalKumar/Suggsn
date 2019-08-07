package com.kunaalkumar.sugsn.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.RecyclerViewAdapter
import com.kunaalkumar.sugsn.movies.viewModels.FactoryViewModel
import com.kunaalkumar.sugsn.movies.viewModels.ChildMovieViewModel
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import java.lang.Exception

class ChildMoviesFragment : Fragment() {

    companion object {
        val VIEW_MODEL_TYPE_KEY = "ViewModelTypeKey"
        val LAYOUT_MANAGER_KEY = "LayoutManagerKey"
    }

    lateinit var viewModel: ChildMovieViewModel
    val linearLayoutManager = LinearLayoutManager(context)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(
            VIEW_MODEL_TYPE_KEY,
            arguments!!.getSerializable(VIEW_MODEL_TYPE_KEY)
        )
    }

    override fun onPause() {
        super.onPause()
        viewModel.listState = linearLayoutManager.onSaveInstanceState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments == null)
            throw Exception("Argument VIEW_MODEL_TYPE_KEY must be set")

        viewModel = ViewModelProviders.of(
            this,
            FactoryViewModel(arguments!!.getSerializable(VIEW_MODEL_TYPE_KEY) as MOVIE_TYPE)
        ).get(ChildMovieViewModel::class.java)

        if (!viewModel.isAdapterInitialized) {
            viewModel.adapter = RecyclerViewAdapter()
            viewModel.isAdapterInitialized = true
        }

        if (viewModel.listState != null) {
            linearLayoutManager.onRestoreInstanceState(viewModel.listState)
        }

        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = viewModel.adapter
        recycler_view.setItemViewCacheSize(20)
        recycler_view.hasFixedSize()

        // Scroll additional items once user reaches end of list
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextTenItems()
                }
            }
        })

    }

}
