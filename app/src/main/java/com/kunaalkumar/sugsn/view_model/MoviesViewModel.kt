package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.repositories.TmdbRepository.MOVIES_NOW_PLAYING
import com.kunaalkumar.sugsn.repositories.TmdbRepository.MOVIES_POPULAR
import com.kunaalkumar.sugsn.repositories.TmdbRepository.MOVIES_TOP_RATED
import com.kunaalkumar.sugsn.repositories.TmdbRepository.MOVIES_UPCOMING
import com.kunaalkumar.sugsn.tmdb.TMDbCallback
import com.kunaalkumar.sugsn.tmdb.TMDbItem

class MoviesViewModel : ViewModel() {

    val TAG: String = "Sugsn@MoviesViewModel"

    /**
     * 0 -> Popular
     * 1 -> Top Rated
     * 2 -> Upcoming
     * 3 -> Now Playing
     */
    companion object {
        const val POPULAR = 0
        const val TOP_RATED = 1
        const val UPCOMING = 2
        const val NOW_PLAYING = 3
    }

    private val callbacks = Array<LiveData<TMDbCallback<TMDbItem>>>(4) { MutableLiveData<TMDbCallback<TMDbItem>>() }
    private val lastPages = Array(4) { -1 }
    private val currentPages = Array(4) {
        MutableLiveData<Int>().apply {
            value = 1
        }
    }


    init {
        callbacks[POPULAR] = Transformations.switchMap(currentPages[POPULAR]) {
            return@switchMap TmdbRepository.getMovies(MOVIES_POPULAR, it)
        }

        callbacks[TOP_RATED] = Transformations.switchMap(currentPages[TOP_RATED]) {
            return@switchMap TmdbRepository.getMovies(MOVIES_TOP_RATED, it)
        }

        callbacks[UPCOMING] = Transformations.switchMap(currentPages[UPCOMING]) {
            return@switchMap TmdbRepository.getMovies(MOVIES_UPCOMING, it)
        }

        callbacks[NOW_PLAYING] = Transformations.switchMap(currentPages[NOW_PLAYING]) {
            return@switchMap TmdbRepository.getMovies(MOVIES_NOW_PLAYING, it)
        }
    }

    fun getMovies(type: Int): LiveData<TMDbCallback<TMDbItem>> {
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