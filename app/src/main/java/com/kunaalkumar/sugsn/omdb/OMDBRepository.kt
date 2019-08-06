package com.kunaalkumar.sugsn.omdb

import com.kunaalkumar.sugsn.BuildConfig
import com.kunaalkumar.sugsn.util.RetrofitFactory

object OMDBRepository {

    private val TAG: String = "OMDBRepository"
    private val omdbService = RetrofitFactory.makeOMDBRetrofitService()

    suspend fun getRottenRating(imdbId: String): String {
        val itemObj = omdbService.getRottenLink(BuildConfig.OMDB_API_KEY, imdbId)
        itemObj.ratings?.forEach { movieRatings ->
            if (movieRatings.source.equals("Rotten Tomatoes"))
                return movieRatings.value
        }
        return "Not Found"
    }
}
