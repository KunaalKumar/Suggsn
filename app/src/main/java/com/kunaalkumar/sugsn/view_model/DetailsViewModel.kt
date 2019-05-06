package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.tmdb.TMDbMovieItem

class DetailsViewModel : ViewModel() {
    val TAG: String = "Sugsn@DetailsViewModel"

    private var currentCallback = MediatorLiveData<TMDbMovieItem>()

    private val _name = MutableLiveData<String>()
    private val _rating = MutableLiveData<String>()
    private val _image = MutableLiveData<String>()
    private val _backdrop = MutableLiveData<String>()
    private val _runtime = MutableLiveData<String>()

    val name: LiveData<String> = _name
    val rating: LiveData<String> = _rating
    val image: LiveData<String> = _image
    val backdrop: LiveData<String> = _backdrop
    val runtime: LiveData<String> = _runtime

    fun loadDetails(id: Int): LiveData<TMDbMovieItem> {
        currentCallback.addSource(TmdbRepository.getMovieDetails(id)) {
            _name.value = it.original_title
            _rating.value = it.vote_average.toString()
            _image.value = it.getPoster().toString()
            _backdrop.value = it.getBackdrop().toString()
            _runtime.value = it.runtime.toString().plus("m")
        }
        return currentCallback
    }
}