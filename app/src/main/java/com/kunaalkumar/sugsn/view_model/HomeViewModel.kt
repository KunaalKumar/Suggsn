package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TraktRepository
import com.kunaalkumar.sugsn.trakt.TraktResponse
import com.kunaalkumar.sugsn.trakt.TraktTrendingMovies

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

    private val callbacks =
        Array<LiveData<TraktResponse<List<TraktTrendingMovies>>>>(2) { MutableLiveData<TraktResponse<List<TraktTrendingMovies>>>() }
    private val lastPages = Array(2) { -1 }
    private val currentPages = Array(2) {
        MutableLiveData<Int>().apply { value = 1 }
    }

    init {
        callbacks[MOVIES] = Transformations.switchMap(currentPages[MOVIES]) {
            return@switchMap TraktRepository.getTrendingMovies(it)
        }

//        callbacks[SHOWS] = Transformations.switchMap(currentPages[SHOWS]) {
//            return@switchMap TmdbRepository.getDiscover(DISCOVER_TV, it)
//        }
    }

    fun getTrending(type: Int): LiveData<TraktResponse<List<TraktTrendingMovies>>> {
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