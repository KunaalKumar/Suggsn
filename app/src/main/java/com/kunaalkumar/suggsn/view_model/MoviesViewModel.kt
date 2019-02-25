package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.MOVIES_POPULAR
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class MoviesViewModel : ViewModel() {

    val TAG: String = "Suggsn@MoviesViewModel"

    private var popularCurrentPage: Int = 0
    private var lastPopularPage: Int = 0

    private var tmdbRepo = TmdbRepository.instance
    private var currentCallback = MediatorLiveData<TMDbCallback>()

    private var popularList = MediatorLiveData<ArrayList<TMDbItem>>()

    fun getMovies(type: String): LiveData<TMDbCallback> {
        when (type) {
            MOVIES_POPULAR -> {
                currentCallback.addSource(tmdbRepo.getMovies(MOVIES_POPULAR, 1)) {
                    popularCurrentPage = it.page
                    lastPopularPage = it.total_pages
                    popularList.postValue(ArrayList(it.results))
                }
            }
        }
        return currentCallback
    }

    fun nextPage(type: String) {
        when (type) {
            MOVIES_POPULAR ->
                if (popularCurrentPage != lastPopularPage) {
                    popularList.addSource(tmdbRepo.getMovies(MOVIES_POPULAR, ++popularCurrentPage)) {
                        popularList.value!!.addAll(it.results)
                        popularList.value = popularList.value
                    }
                }
        }

    }

    fun getPopularList(): LiveData<ArrayList<TMDbItem>> {
        return popularList
    }
}