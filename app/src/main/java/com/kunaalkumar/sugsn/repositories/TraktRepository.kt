package com.kunaalkumar.sugsn.repositories

import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.sugsn.trakt.TraktResponse
import com.kunaalkumar.sugsn.trakt.TraktTrendingMovies
import com.kunaalkumar.sugsn.util.RetrofitFactory

object TraktRepository {
    private val TAG: String = "TraktRepository"
    private val traktService = RetrofitFactory.makeTraktRetrofitService()

    fun getTrendingMovies(page: Int): MutableLiveData<TraktResponse<List<TraktTrendingMovies>>> {
        val data = MutableLiveData<TraktResponse<List<TraktTrendingMovies>>>()

        traktService.trendingMovies(page).enqueue(TraktCallbackWrapper(data))

        return data
    }
}