package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.TMDbMovieItem
import com.kunaalkumar.sugsn.tmdb.TMDbVideos

class DetailsViewModel : ViewModel() {
    val TAG: String = "Sugsn@DetailsViewModel"

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