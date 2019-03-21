package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.TMDbCallback
import com.kunaalkumar.sugsn.tmdb.TMDbItem

class PeopleViewModel : ViewModel() {

    val TAG: String = "Sugsn@PeopleViewModel"

    private var currentPage: Int = 0
    private var lastPage: Int = 0

    private var popularPeopleList = MediatorLiveData<ArrayList<TMDbItem>>()
    
    private var currentCallback = MediatorLiveData<TMDbCallback<TMDbItem>>()

    fun getPopularPeople(): LiveData<TMDbCallback<TMDbItem>> {
        currentCallback.addSource(TmdbRepository.getPopularPeople(1)) {
            currentPage = it.page
            lastPage = it.total_pages
            popularPeopleList.postValue(ArrayList(it.results))
        }

        return currentCallback
    }

    fun nextPage() {
        if (currentPage != lastPage) {
            popularPeopleList.addSource(TmdbRepository.getPopularPeople(++currentPage)) {
                popularPeopleList.value!!.addAll(it.results)
                popularPeopleList.value = popularPeopleList.value
            }
        }
    }

    fun getPopularPeopleList(): LiveData<ArrayList<TMDbItem>> {
        return popularPeopleList
    }
}