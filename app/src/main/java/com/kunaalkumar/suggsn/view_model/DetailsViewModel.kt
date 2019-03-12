package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbMovieItem

class DetailsViewModel : ViewModel() {
    val TAG: String = "Suggsn@DetailsViewModel"

    private var tmdbRepo = TmdbRepository.instance
    private var currentCallback = MediatorLiveData<TMDbCallback<TMDbMovieItem>>()
}