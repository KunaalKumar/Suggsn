package com.kunaalkumar.suggsn.tmdb

import com.kunaalkumar.suggsn.BuildConfig
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Reference: https://developers.themoviedb.org/3
 */
interface ITMDbService {

    @GET("search/multi?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US")
    fun searchMulti(
        @Query("query") query: String, @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean
    ): Deferred<Response<TMDbCallback>>

    @GET("configuration?api_key=${BuildConfig.TMDb_API_KEY}")
    fun config(): Deferred<Response<TMDbConfigCallback>>
}