package com.kunaalkumar.sugsn.omdb

import com.kunaalkumar.sugsn.BuildConfig
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object OMDBRepository {

    private val TAG: String = "OMDBRepository"
    private val omdbService = RetrofitFactory.makeOMDBRetrofitService()

    fun getRottenRating(imdbId: String): Observable<String> {
        return omdbService.getRottenLink(BuildConfig.OMDB_API_KEY, imdbId).map { movie ->
            movie.ratings.forEach { movieRatings ->
                if (movieRatings.source.equals("Rotten Tomatoes"))
                    return@map movieRatings.value
            }
            return@map "Not Found"
        }.subscribeOn(Schedulers.io())
    }
}
