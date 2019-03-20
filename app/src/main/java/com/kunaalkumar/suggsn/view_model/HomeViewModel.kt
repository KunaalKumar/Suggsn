package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.repositories.TmdbRepository.DISCOVER_MOVIE
import com.kunaalkumar.suggsn.repositories.TmdbRepository.DISCOVER_TV
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class HomeViewModel : ViewModel() {

    val TAG: String = "Suggsn@HomeViewModel"

    private var currentMoviesPage = MutableLiveData<Int>()
    private var lastMoviesPage: Int = 0

    private var currentShowsPage = MutableLiveData<Int>()
    private var lastShowsPage: Int = 0

    private lateinit var moviesCallback: LiveData<TMDbCallback<TMDbItem>>
    private lateinit var showsCallback: LiveData<TMDbCallback<TMDbItem>>

    init {
        currentMoviesPage.value = 1
        currentShowsPage.value = 1

        moviesCallback = Transformations.switchMap(currentMoviesPage) {
            return@switchMap TmdbRepository.getDiscover(DISCOVER_MOVIE, it)
        }

        showsCallback = Transformations.switchMap(currentShowsPage) {
            return@switchMap TmdbRepository.getDiscover(DISCOVER_TV, it)
        }
    }

    fun getDiscover(type: String): LiveData<TMDbCallback<TMDbItem>> {
        return when (type) {
            DISCOVER_MOVIE -> {
                moviesCallback
            }
            // Else TV
            else -> {
                showsCallback
            }
        }
    }

    fun nextPage(type: String) {
//        when (type) {
//            DISCOVER_MOVIE -> if (currentMoviesPage != lastMoviesPage)
//                currentMoviesPage++
//
//            DISCOVER_TV -> if (currentShowsPage != lastShowsPage)
//                currentShowsPage++
//        }

//        when (type) {
//            DISCOVER_MOVIE -> {
//                if (currentMoviesPage != lastMoviesPage) {
//                    moviesList.addSource(TmdbRepository.getDiscover(DISCOVER_MOVIE, ++currentMoviesPage)) {
//                        moviesList.value!!.addAll(it.results)
//                        moviesList.value = moviesList.value
//                    }
//                }
//            }
//            DISCOVER_TV -> {
//                if (currentShowsPage != lastShowsPage) {
//                    showsList.addSource(TmdbRepository.getDiscover(DISCOVER_TV, ++currentShowsPage)) {
//                        showsList.value!!.addAll(it.results)
//                        showsList.value = showsList.value
//                    }
//                }
//            }
//        }
    }
}