package com.kunaalkumar.suggsn.results_components

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class ResultsAdapter(private val mediaType: String?) : RecyclerView.Adapter<ResultViewHolder>() {

    private val TAG: String = "ResultsAdapter"
    private val dataSet = ArrayList<TMDbItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
//        val height = parent.measuredHeight.
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: Binding $position")
        holder.bindView(dataSet[position], mediaType)
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