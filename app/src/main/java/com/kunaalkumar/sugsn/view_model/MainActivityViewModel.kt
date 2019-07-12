package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.imdb.ImdbRepository

class MainActivityViewModel : ViewModel() {

    val TAG: String = "Sugsn@MainActivityViewModel"

    val topRatedMoviesList by lazy {
        ImdbRepository.getTopRatedMovies()
    }
}
