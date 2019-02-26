package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.MOVIES_POPULAR
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.MOVIES_TOP_RATED
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.MOVIES_UPCOMING
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class MoviesViewModel : ViewModel() {

    val TAG: String = "Suggsn@MoviesViewModel"

    private var popularCurrentPage: Int = 0
    private var topRatedCurrentPage: Int = 0
    private var upcomingCurrentPage: Int = 0

    private var lastPopularPage: Int = 0
    private var lastTopRatedPage: Int = 0
    private var lastUpcomingPage: Int = 0

    private var popularList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var topRatedList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var upcomingList = MediatorLiveData<ArrayList<TMDbItem>>()

    private var tmdbRepo = TmdbRepository.instance
    private var currentCallback = MediatorLiveData<TMDbCallback>()


    fun getMovies(type: String): LiveData<TMDbCallback> {
        when (type) {
            MOVIES_POPULAR ->
                currentCallback.addSource(tmdbRepo.getMovies(MOVIES_POPULAR, 1)) {
                    popularCurrentPage = it.page
                    lastPopularPage = it.total_pages
                    popularList.postValue(ArrayList(it.results))
                }

            MOVIES_TOP_RATED ->
                currentCallback.addSource(tmdbRepo.getMovies(MOVIES_TOP_RATED, 1)) {
                    topRatedCurrentPage = it.page
                    lastTopRatedPage = it.total_pages
                    topRatedList.postValue(ArrayList(it.results))
                }

            MOVIES_UPCOMING ->
                currentCallback.addSource(tmdbRepo.getMovies(MOVIES_UPCOMING, 1)) {
                    upcomingCurrentPage = it.page
                    lastUpcomingPage = it.total_pages
                    upcomingList.postValue(ArrayList(it.results))
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

            MOVIES_TOP_RATED ->
                if (topRatedCurrentPage != lastTopRatedPage) {
                    topRatedList.addSource(tmdbRepo.getMovies(MOVIES_TOP_RATED, ++topRatedCurrentPage)) {
                        topRatedList.value!!.addAll(it.results)
                        topRatedList.value = topRatedList.value
                    }
                }

            MOVIES_UPCOMING ->
                if (upcomingCurrentPage != lastUpcomingPage) {
                    upcomingList.addSource(tmdbRepo.getMovies(MOVIES_UPCOMING, ++upcomingCurrentPage)) {
                        upcomingList.value!!.addAll(it.results)
                        upcomingList.value = upcomingList.value
                    }
                }
        }

    }

    fun getPopularList(): LiveData<ArrayList<TMDbItem>> {
        return popularList
    }

    fun getTopRatedList(): LiveData<ArrayList<TMDbItem>> {
        return topRatedList
    }

    fun getUpcomingList(): LiveData<ArrayList<TMDbItem>> {
        return upcomingList
    }
}