package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class MainViewModel : ViewModel() {

    val TAG: String = "Suggsn@MainViewModel"

    private lateinit var searchResults: LiveData<ArrayList<TMDbItem>>
    private lateinit var backdropImageUrl: LiveData<String>
    var tmdbRepo: TmdbRepository = TmdbRepository()

//    fun getSearchResults(query: String, pageNum: Int, includeAdult: Boolean)
//            : LiveData<ArrayList<TMDbItem>> {
//        searchResults = tmdbRepo.getSearchResults(query, pageNum, includeAdult)
//        return searchResults
//    }

    fun getBackdropImageUrl(): LiveData<String> {
        backdropImageUrl = tmdbRepo.getBackdropUrl()
        return backdropImageUrl
    }

    fun getConfigStatus(): LiveData<Boolean> {
        return tmdbRepo.getConfigStatus()
    }

}