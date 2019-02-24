package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class HomeViewModel : ViewModel() {

    val TAG: String = "Suggsn@HomeViewModel"

    private var currentPage: Int = 0
    private var lastPage: Int = 0

    private var tmdbRepo = TmdbRepository.instance
    private var moviesList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var currentCallback = MediatorLiveData<TMDbCallback>()

    fun getDiscoverMovies(): LiveData<TMDbCallback> {
        currentCallback.addSource(tmdbRepo.getDiscoverMovies(1)) {
            currentPage = it.page
            lastPage = it.total_pages
            moviesList.postValue(ArrayList(it.results))
        }
        return currentCallback
    }

    fun getMovieList(): LiveData<ArrayList<TMDbItem>> {
        return moviesList
    }

    fun nextPage() {
        if (currentPage != lastPage) {
            moviesList.addSource(tmdbRepo.getDiscoverMovies(++currentPage)) {
                moviesList.value!!.addAll(it.results)
                moviesList.value = moviesList.value
            }
        }
    }

}