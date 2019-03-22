package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.repositories.TmdbRepository.DISCOVER_MOVIE
import com.kunaalkumar.sugsn.repositories.TmdbRepository.DISCOVER_TV
import com.kunaalkumar.sugsn.tmdb.TMDbCallback
import com.kunaalkumar.sugsn.tmdb.TMDbItem

class HomeViewModel : ViewModel() {

    val TAG: String = "Sugsn@HomeViewModel"

    /**
     * 0 -> Movies
     * 1 -> Shows
     */
    companion object {
        const val MOVIES = 0
        const val SHOWS = 1
    }

    private val callbacks = Array<LiveData<TMDbCallback<TMDbItem>>>(2) { MutableLiveData<TMDbCallback<TMDbItem>>() }
    private val lastPages = Array(2) { -1 }
    private val currentPages = Array(2) {
        MutableLiveData<Int>().apply { value = 1 }
    }

    init {
        callbacks[MOVIES] = Transformations.switchMap(currentPages[MOVIES]) {
            return@switchMap TmdbRepository.getDiscover(DISCOVER_MOVIE, it)
        }

        callbacks[SHOWS] = Transformations.switchMap(currentPages[SHOWS]) {
            return@switchMap TmdbRepository.getDiscover(DISCOVER_TV, it)
        }
    }

    fun getDiscover(type: Int): LiveData<TMDbCallback<TMDbItem>> {
        return callbacks[type]
    }

    fun nextPage(type: Int) {
        if (currentPages[type].value!! <= lastPages[type]) {
            currentPages[type].value = currentPages[type].value!! + 1
        }
    }

    fun setLastPage(type: Int, page: Int) {
        lastPages[type] = page
    }
}