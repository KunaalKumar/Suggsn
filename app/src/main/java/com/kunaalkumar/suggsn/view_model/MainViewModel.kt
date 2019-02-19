package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class MainViewModel : ViewModel() {

    val TAG: String = "Suggsn@MainViewModel"
    private var tmdbRepo = TmdbRepository.instance

    private var currentPage: Int = 1
    private var maxPage: Int = 0

    private lateinit var backdropImageUrl: MutableLiveData<String>
    private var searchResults = MediatorLiveData<ArrayList<TMDbItem>>()

    fun getSearchResults(query: String, pageNum: Int, includeAdult: Boolean)
            : LiveData<ArrayList<TMDbItem>> {
        searchResults.addSource(tmdbRepo.getSearchResults(query, pageNum, includeAdult)) {
            searchResults.postValue(it)
        }
        TODO("Implement callback watcher and arraylist watcher")
        return searchResults
    }

    fun nextPage() {
        searchResults.addSource(tmdbRepo.getSearchResults("Drive", ++currentPage, false)) {
            searchResults.value!!.addAll(it)
            searchResults.value = searchResults.value
        }
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