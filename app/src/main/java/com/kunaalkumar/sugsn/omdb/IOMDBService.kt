package com.kunaalkumar.sugsn.omdb

import com.kunaalkumar.sugsn.util.OMDB_Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IOMDBService {

    @GET("?tomatoes=true")
    fun getRottenLink(@Query("apikey") apiKey: String, @Query("i") imdb_id: String): Observable<OMDB_Result>
}