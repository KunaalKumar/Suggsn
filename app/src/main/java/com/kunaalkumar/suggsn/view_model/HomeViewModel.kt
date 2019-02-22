package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class HomeViewModel : ViewModel() {

    val TAG: String = "Suggsn@HomeViewModel"
    private var tmdbRepo = TmdbRepository.instance
    private var moviesList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var currentCallback = MediatorLiveData<TMDbCallback>()

    fun getDiscoverMovies(): LiveData<TMDbCallback> {
        currentCallback.addSource(tmdbRepo.getDiscoverMovies()) {
            moviesList.postValue(ArrayList(it.results))
        }
        return currentCallback
    }

    fun getMovieList(): LiveData<ArrayList<TMDbItem>> {
        return moviesList
    }
}