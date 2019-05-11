package com.kunaalkumar.sugsn.results_components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.R
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.trakt.TraktTrendingMovies
import kotlinx.android.synthetic.main.result_list_item.view.*

class TraktResultAdapter<T>(val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<TraktResultViewHolder>() {

    private val dataSet = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraktResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
        return TraktResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: TraktResultViewHolder, position: Int) {

        holder.itemView.poster.layoutParams.width = TmdbRepository.WIDTH
        holder.itemView.poster.layoutParams.height = TmdbRepository.HEIGHT

        TmdbRepository.getMovieDetails((dataSet as ArrayList<TraktTrendingMovies>)[position].movie.ids.tmdb)
            .observe(lifecycleOwner, Observer {
                holder.bindView(it)
            })
    }

    fun addResults(results: List<T>) {
        dataSet.addAll(ArrayList(results))
        notifyDataSetChanged()
    }
}