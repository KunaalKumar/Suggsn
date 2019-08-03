package com.kunaalkumar.sugsn.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.MainActivity
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.movies.viewModels.PopularMoviesViewModel
import com.kunaalkumar.sugsn.movies.viewModels.TopRatedMoviesViewModel
import com.kunaalkumar.sugsn.movies.viewModels.VMWrapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class ChildMoviesFragment : Fragment() {

    companion object {
        const val TOP_MOVIE = "TOP_MOVIE"
        const val POPULAR_MOVIE = "POPULAR_MOVIE"
    }

    lateinit var viewModel: VMWrapper

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("viewModelType", arguments!!.getString("viewModelType"))
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
            return
        when (arguments!!.getString("viewModelType")) {
            TOP_MOVIE -> {
                viewModel = ViewModelProviders.of(this)
                    .get(TopRatedMoviesViewModel::class.java)
            }
            POPULAR_MOVIE -> viewModel = ViewModelProviders.of(this).get(
                PopularMoviesViewModel::class.java
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = viewModel.adapter
        recycler_view.setItemViewCacheSize(20)
        recycler_view.hasFixedSize()

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this.adapter
        }

        // Scroll additional items once user reaches end of list
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextTenItems()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val fab = (activity as MainActivity).search_fab
                if (dy > 0) {
                    fab.hide()
                } else if (dy < 0) {
                    fab.show()
                }
            }
        })

    }

}
