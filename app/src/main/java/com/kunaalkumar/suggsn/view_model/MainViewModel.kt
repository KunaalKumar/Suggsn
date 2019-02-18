package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class MainViewModel : ViewModel() {

    val TAG: String = "Suggsn@MainViewModel"
    private var tmdbRepo: TmdbRepository = TmdbRepository()

    private lateinit var searchResults: LiveData<ArrayList<TMDbItem>>
    private lateinit var backdropImageUrl: LiveData<String>

    fun getSearchResults(query: String, pageNum: Int, includeAdult: Boolean)
            : LiveData<ArrayList<TMDbItem>> {
        val data = tmdbRepo.getSearchResults(query, pageNum, includeAdult)
        if (pageNum != 1) {
            searchResults.value?.addAll(data.value!!)
            android.util.Log.d(TAG, "getSearchResults: Updated dataSet")
        } else {
            searchResults = data
            android.util.Log.d(TAG, "getSearchResults: New dataSet")
        }
        return searchResults
    }

    fun getBackdropImageUrl(): LiveData<String> {
        // Check to see if image exists in cache
        if (!::backdropImageUrl.isInitialized) {
            backdropImageUrl = tmdbRepo.getBackdropUrl()
        }
        return backdropImageUrl
    }

    fun getConfigStatus(): LiveData<Boolean> {
        return tmdbRepo.getConfigStatus()
    }

}