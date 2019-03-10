package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.DISCOVER_MOVIE
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.DISCOVER_TV
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class HomeViewModel : ViewModel() {

    val TAG: String = "Suggsn@HomeViewModel"

    private var currentMoviesPage: Int = 0
    private var lastMoviesPage: Int = 0

    private var currentShowsPage: Int = 0
    private var lastShowsPage: Int = 0

    private var tmdbRepo = TmdbRepository.instance
    private var currentCallback = MediatorLiveData<TMDbCallback<TMDbItem>>()

    private var moviesList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var showsList = MediatorLiveData<ArrayList<TMDbItem>>()

    fun getDiscover(type: String): LiveData<TMDbCallback<TMDbItem>> {
        when (type) {
            DISCOVER_MOVIE ->
                currentCallback.addSource(tmdbRepo.getDiscover(DISCOVER_MOVIE, 1)) {
                    currentMoviesPage = it.page
                    lastMoviesPage = it.total_pages
                    moviesList.postValue(ArrayList(it.results))
                }

            DISCOVER_TV ->
                currentCallback.addSource(tmdbRepo.getDiscover(DISCOVER_TV, 1)) {
                    currentShowsPage = it.page
                    lastShowsPage = it.total_pages
                    showsList.postValue(ArrayList(it.results))
                }
        }
        return currentCallback
    }

    fun getMovieList(): LiveData<ArrayList<TMDbItem>> {
        return moviesList
    }

    fun getTVList(): LiveData<ArrayList<TMDbItem>> {
        return showsList
    }

    fun nextPage(type: String) {
        when (type) {
            DISCOVER_MOVIE -> {
                if (currentMoviesPage != lastMoviesPage) {
                    moviesList.addSource(tmdbRepo.getDiscover(DISCOVER_MOVIE, ++currentMoviesPage)) {
                        moviesList.value!!.addAll(it.results)
                        moviesList.value = moviesList.value
                    }
                }
            }
            DISCOVER_TV -> {
                if (currentShowsPage != lastShowsPage) {
                    showsList.addSource(tmdbRepo.getDiscover(DISCOVER_TV, ++currentShowsPage)) {
                        showsList.value!!.addAll(it.results)
                        showsList.value = showsList.value
                    }
                }
            }
        }

    }

}