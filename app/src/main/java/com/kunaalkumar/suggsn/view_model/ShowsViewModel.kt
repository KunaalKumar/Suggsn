package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.SHOWS_AIRING_TODAY
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.SHOWS_ON_AIR
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.SHOWS_POPULAR
import com.kunaalkumar.suggsn.repositories.TmdbRepository.Companion.SHOWS_TOP_RATED
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class ShowsViewModel : ViewModel() {

    val TAG: String = "Suggsn@ShowsViewModel"

    private var popularCurrentPage: Int = 0
    private var topRatedCurrentPage: Int = 0
    private var onAirCurrentPage: Int = 0
    private var airTodayCurrentPage: Int = 0

    private var lastPopularPage: Int = 0
    private var lastTopRatedPage: Int = 0
    private var lastOnAirPage: Int = 0
    private var lastAiringTodayPage: Int = 0

    private var popularList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var topRatedList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var onAirList = MediatorLiveData<ArrayList<TMDbItem>>()
    private var airingTodayList = MediatorLiveData<ArrayList<TMDbItem>>()

    private var tmdbRepo = TmdbRepository.instance
    private var currentCallback = MediatorLiveData<TMDbCallback>()

    fun getShows(type: String): LiveData<TMDbCallback> {
        when (type) {
            SHOWS_POPULAR ->
                currentCallback.addSource(tmdbRepo.getShows(SHOWS_POPULAR, 1)) {
                    popularCurrentPage = it.page
                    lastPopularPage = it.total_pages
                    popularList.postValue(ArrayList(it.results))
                }

            SHOWS_TOP_RATED ->
                currentCallback.addSource(tmdbRepo.getShows(SHOWS_TOP_RATED, 1)) {
                    topRatedCurrentPage = it.page
                    lastTopRatedPage = it.total_pages
                    topRatedList.postValue(ArrayList(it.results))
                }

            SHOWS_ON_AIR ->
                currentCallback.addSource(tmdbRepo.getShows(SHOWS_ON_AIR, 1)) {
                    onAirCurrentPage = it.page
                    lastOnAirPage = it.total_pages
                    onAirList.postValue(ArrayList(it.results))
                }

            SHOWS_AIRING_TODAY ->
                currentCallback.addSource(tmdbRepo.getShows(SHOWS_AIRING_TODAY, 1)) {
                    airTodayCurrentPage = it.page
                    lastAiringTodayPage = it.total_pages
                    airingTodayList.postValue(ArrayList(it.results))
                }
        }
        return currentCallback
    }

    fun nextPage(type: String) {
        when (type) {
            SHOWS_POPULAR ->
                if (popularCurrentPage != lastPopularPage) {
                    popularList.addSource(tmdbRepo.getShows(SHOWS_POPULAR, ++popularCurrentPage)) {
                        popularList.value!!.addAll(it.results)
                        popularList.value = popularList.value
                    }
                }

            SHOWS_TOP_RATED ->
                if (topRatedCurrentPage != lastTopRatedPage) {
                    topRatedList.addSource(tmdbRepo.getShows(SHOWS_TOP_RATED, ++topRatedCurrentPage)) {
                        topRatedList.value!!.addAll(it.results)
                        topRatedList.value = topRatedList.value
                    }
                }

            SHOWS_ON_AIR ->
                if (onAirCurrentPage != lastOnAirPage) {
                    onAirList.addSource(tmdbRepo.getShows(SHOWS_ON_AIR, ++onAirCurrentPage)) {
                        onAirList.value!!.addAll(it.results)
                        onAirList.value = onAirList.value
                    }
                }

            SHOWS_AIRING_TODAY ->
                if (airTodayCurrentPage != lastAiringTodayPage) {
                    airingTodayList.addSource(tmdbRepo.getShows(SHOWS_AIRING_TODAY, ++airTodayCurrentPage)) {
                        airingTodayList.value!!.addAll(it.results)
                        airingTodayList.value = airingTodayList.value
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

    fun getOnAirList(): LiveData<ArrayList<TMDbItem>> {
        return onAirList
    }

    fun getAiringTodayList(): LiveData<ArrayList<TMDbItem>> {
        return airingTodayList
    }
}