package com.kunaalkumar.suggsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import com.kunaalkumar.suggsn.tmdb.TMDbMovieItem
import com.kunaalkumar.suggsn.tmdb.TMDbVideos

class DetailsViewModel : ViewModel() {
    val TAG: String = "Suggsn@DetailsViewModel"

    private var currentCallback = MediatorLiveData<TMDbMovieItem>()
    private var movieData = MediatorLiveData<TMDbMovieItem>()
    private var movieVideosCallback = MediatorLiveData<TMDbVideos>()
    private var movieVideos = MediatorLiveData<TMDbVideos>()

    fun getMovieDetails(id: Int): LiveData<TMDbMovieItem> {
        currentCallback.addSource(TmdbRepository.getMovieDetails(id)) {
            movieData.postValue(it)
        }
        return currentCallback
    }

    fun getMovieData(): LiveData<TMDbMovieItem> {
        return movieData
    }

    fun getMovieVideos(id: Int): LiveData<TMDbVideos> {
        movieVideosCallback.addSource(TmdbRepository.getMovieVideos(id)) {
            movieVideos.postValue(it)
        }
        return movieVideosCallback
    }

    fun getMovieVideos(): LiveData<TMDbVideos> {
        return movieVideos
    }
}