package com.kunaalkumar.sugsn.omdb

import com.kunaalkumar.sugsn.BuildConfig
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object OMDBRepository {

    private val TAG: String = "OMDBRepository"
    private val omdbService = RetrofitFactory.makeOMDBRetrofitService()

    fun getRottenRating(imdbId: String): Observable<String> {
        return omdbService.getRottenLink(BuildConfig.OMDB_API_KEY, imdbId).map {
            it.Ratings.forEach {
                if (it.Source.equals("Rotten Tomatoes"))
                    return@map it.Value
            }
            return@map it.Ratings[0].Value
        }.subscribeOn(Schedulers.io())
    }
}
