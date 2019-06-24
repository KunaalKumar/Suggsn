package com.kunaalkumar.sugsn.results_components

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.tmdb.TMDbItem

class TmdbResultsAdapter(private val mediaType: String?) : RecyclerView.Adapter<TmdbResultViewHolder>() {

    private val TAG: String = "TmdbResultsAdapter"
    private val dataSet = ArrayList<TMDbItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TmdbResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
//        val height = parent.measuredHeight.
        return TmdbResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holderTmdb: TmdbResultViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: Binding $position")
        holderTmdb.bindView(dataSet[position], mediaType)
    }

    // Clear dataSet and add contents of results into it
    fun setResults(results: ArrayList<TMDbItem>) {
        dataSet.clear()
        dataSet.addAll(results)
        notifyDataSetChanged()
    }

    // Append results into dataSet
    fun addResults(results: ArrayList<TMDbItem>) {
        dataSet.addAll(results)
        notifyDataSetChanged()
    }
}