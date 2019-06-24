package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.TMDbMovieDetails

class DetailsViewModel : ViewModel() {
    val TAG: String = "Sugsn@DetailsViewModel"

    fun loadDetails(id: Int): LiveData<TMDbMovieDetails> {
        return TmdbRepository.getMovieDetails(id)
    }
}