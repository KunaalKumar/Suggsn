package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.TMDbCallback
import com.kunaalkumar.sugsn.tmdb.TMDbItem

class SearchViewModel : ViewModel() {

    val TAG: String = "Sugsn@SearchViewModel"

    private var currentPage: Int = 0
    private var lastPage: Int = 0
    private var currentQuery = String()

    private lateinit var backdropImageUrl: MutableLiveData<String>
    private var searchResults = MediatorLiveData<ArrayList<TMDbItem>>()
    private var searchCallback = MediatorLiveData<TMDbCallback<TMDbItem>>()

    fun searchFor(query: String, pageNum: Int, includeAdult: Boolean)
            : LiveData<TMDbCallback<TMDbItem>> {
        currentQuery = query
        searchCallback.addSource(TmdbRepository.getSearchResults(query, pageNum, includeAdult)) {
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
            searchResults.addSource(TmdbRepository.getSearchResults(currentQuery, ++currentPage, false)) {
                searchResults.value!!.addAll(it.results)
                searchResults.value = searchResults.value
            }
        }
    }

    fun getBackdropImageUrl(): LiveData<String> {
        // Check to see if image exists in cache
        if (!::backdropImageUrl.isInitialized) {
            backdropImageUrl = TmdbRepository.getBackdropUrl()
        }
        return backdropImageUrl
    }

    fun getConfigStatus(): LiveData<Boolean> {
        return TmdbRepository.getConfigStatus()
    }
}