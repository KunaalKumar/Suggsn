package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.repositories.TmdbRepository.SHOWS_AIRING_TODAY
import com.kunaalkumar.sugsn.repositories.TmdbRepository.SHOWS_ON_AIR
import com.kunaalkumar.sugsn.repositories.TmdbRepository.SHOWS_POPULAR
import com.kunaalkumar.sugsn.repositories.TmdbRepository.SHOWS_TOP_RATED
import com.kunaalkumar.sugsn.tmdb.TMDbCallback
import com.kunaalkumar.sugsn.tmdb.TMDbItem

class ShowsViewModel : ViewModel() {

    val TAG: String = "Sugsn@ShowsViewModel"

    /**
     * 0 -> Popular
     * 1 -> Top Rated
     * 2 -> On Air
     * 3 -> Airing Today
     */
    companion object {
        const val POPULAR = 0
        const val TOP_RATED = 1
        const val ON_AIR = 2
        const val AIRING_TODAY = 3
    }

    private val callbacks = Array<LiveData<TMDbCallback<TMDbItem>>>(4) { MutableLiveData<TMDbCallback<TMDbItem>>() }
    private val lastPages = Array(4) { -1 }
    private val currentPages = Array(4) {
        MutableLiveData<Int>().apply { value = 1 }
    }

    init {
        callbacks[POPULAR] = Transformations.switchMap(currentPages[POPULAR]) {
            return@switchMap TmdbRepository.getShows(SHOWS_POPULAR, it)
        }

        callbacks[TOP_RATED] = Transformations.switchMap(currentPages[TOP_RATED]) {
            return@switchMap TmdbRepository.getShows(SHOWS_TOP_RATED, it)
        }

        callbacks[ON_AIR] = Transformations.switchMap(currentPages[ON_AIR]) {
            return@switchMap TmdbRepository.getShows(SHOWS_ON_AIR, it)
        }

        callbacks[AIRING_TODAY] = Transformations.switchMap(currentPages[AIRING_TODAY]) {
            return@switchMap TmdbRepository.getShows(SHOWS_AIRING_TODAY, it)
        }
    }

    fun getShows(type: Int): LiveData<TMDbCallback<TMDbItem>> {
        return callbacks[type]
    }

    fun nextPage(type: Int) {
        if (currentPages[type].value!! <= lastPages[type])
            currentPages[type].value = currentPages[type].value!! + 1
    }

    fun setLastPage(type: Int, page: Int) {
        lastPages[type] = page
    }
}