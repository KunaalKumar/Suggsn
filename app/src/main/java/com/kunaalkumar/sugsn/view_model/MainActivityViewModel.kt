package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.imdb.ListItem
import com.kunaalkumar.sugsn.imdb.ImdbRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel : ViewModel() {

    val TAG: String = "Sugsn@MainActivityViewModel"

    val topRatedMoviesList by lazy {
        ImdbRepository.getTopRatedMovies()
    }
}
