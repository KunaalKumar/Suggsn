package com.kunaalkumar.sugsn.results_components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R

class TraktResultAdapter<T> : RecyclerView.Adapter<TraktResultViewHolder>() {

    private val dataSet = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraktResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
        return TraktResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: TraktResultViewHolder, position: Int) {
        holder.bindView(dataSet[position])
    }

    fun addResults(results: T) {
        dataSet.add(results)
        notifyDataSetChanged()
    }
}