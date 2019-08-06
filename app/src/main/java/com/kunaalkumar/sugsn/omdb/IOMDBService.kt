package com.kunaalkumar.sugsn.omdb

import com.kunaalkumar.sugsn.util.OmdbObject
import retrofit2.http.GET
import retrofit2.http.Query

interface IOMDBService {

    @GET("?tomatoes=true")
    suspend fun getRottenLink(@Query("apikey") apiKey: String, @Query("i") imdb_id: String): OmdbObject
}