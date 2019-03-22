package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.TMDbCallback
import com.kunaalkumar.sugsn.tmdb.TMDbItem

class PeopleViewModel : ViewModel() {

    val TAG: String = "Sugsn@PeopleViewModel"

    private var callback: LiveData<TMDbCallback<TMDbItem>>
    private var lastPage: Int = -1
    private var currentPage = MutableLiveData<Int>().apply { value = 1 }

    init {
        callback = Transformations.switchMap(currentPage) {
            return@switchMap TmdbRepository.getPopularPeople(it)
        }

    }

    fun getPopularPeople(): LiveData<TMDbCallback<TMDbItem>> {
        return callback
    }

    fun nextPage() {
        if (currentPage.value!! != lastPage) {
            currentPage.value = currentPage.value!! + 1
        }
    }

    fun setLastPage(page: Int) {
        lastPage = page
    }
}