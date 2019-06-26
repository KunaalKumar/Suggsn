package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.ImdbRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel() : ViewModel() {

    val TAG: String = "Sugsn@MainActivityViewModel"

    private val compositeDisposable = CompositeDisposable()

    val topRatedMoviesList: LiveData<String> by lazy {
        ImdbRepository.getTopRatedMovies(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
