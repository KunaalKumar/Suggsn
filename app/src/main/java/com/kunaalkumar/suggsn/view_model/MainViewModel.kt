package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class MainViewModel : ViewModel() {

    val TAG: String = "Suggsn@MainViewModel"
    private var tmdbRepo = TmdbRepository.instance

    private var currentPage: Int = 0
    private var lastPage: Int = 0
    private var currentQuery = String()

    private lateinit var backdropImageUrl: MutableLiveData<String>
    private var searchResults = MediatorLiveData<ArrayList<TMDbItem>>()
    private var searchCallback = MediatorLiveData<TMDbCallback>()

    fun searchFor(query: String, pageNum: Int, includeAdult: Boolean)
            : LiveData<TMDbCallback> {
        currentQuery = query
        searchCallback.addSource(tmdbRepo.getSearchResults(query, pageNum, includeAdult)) {
            currentPage = it.page
            lastPage = it.total_pages
            searchResults.postValue(ArrayList(it.results))
        }
        return searchCallback
    }

    fun getResults(): LiveData<ArrayList<TMDbItem>> {
        return searchResults
    }

    fun nextPage() {
        if (currentPage != lastPage) {
            searchResults.addSource(tmdbRepo.getSearchResults(currentQuery, ++currentPage, false)) {
                searchResults.value!!.addAll(it.results)
                searchResults.value = searchResults.value
            }
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