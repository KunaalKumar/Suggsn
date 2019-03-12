package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbMovieItem

class DetailsViewModel : ViewModel() {
    val TAG: String = "Suggsn@DetailsViewModel"

    private var tmdbRepo = TmdbRepository.instance
    private var currentCallback = MediatorLiveData<TMDbMovieItem>()
    private var movieData = MediatorLiveData<TMDbMovieItem>()

    fun getMovieDetails(id: Int): LiveData<TMDbMovieItem> {
        currentCallback.addSource(tmdbRepo.getMovieDetails(id)) {
            movieData.postValue(it)
        }
        return currentCallback
    }

    fun getMovieData(): LiveData<TMDbMovieItem> {
        return movieData
    }
}