package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem

class PeopleViewModel : ViewModel() {

    val TAG: String = "Suggsn@PeopleViewModel"

    private var currentPage: Int = 0
    private var lastPage: Int = 0

    private var popularPeopleList = MediatorLiveData<ArrayList<TMDbItem>>()

    private var tmdbRepo = TmdbRepository.instance
    private var currentCallback = MediatorLiveData<TMDbCallback<TMDbItem>>()

    fun getPopularPeople(): LiveData<TMDbCallback<TMDbItem>> {
        currentCallback.addSource(tmdbRepo.getPopularPeople(1)) {
            currentPage = it.page
            lastPage = it.total_pages
            popularPeopleList.postValue(ArrayList(it.results))
        }

        return currentCallback
    }

    fun nextPage() {
        if (currentPage != lastPage) {
            popularPeopleList.addSource(tmdbRepo.getPopularPeople(++currentPage)) {
                popularPeopleList.value!!.addAll(it.results)
                popularPeopleList.value = popularPeopleList.value
            }
        }
    }

    fun getPopularPeopleList(): LiveData<ArrayList<TMDbItem>> {
        return popularPeopleList
    }
}