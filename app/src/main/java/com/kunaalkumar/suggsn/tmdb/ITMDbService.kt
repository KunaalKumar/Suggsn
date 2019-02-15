package com.kunaalkumar.suggsn.tmdb

import com.kunaalkumar.suggsn.BuildConfig
import retrofit2.Call
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
    ): Call<TMDbCallback>

    @GET("configuration?api_key=${BuildConfig.TMDb_API_KEY}")
    fun config(): Call<TMDbConfigCallback>

    @GET("movie/popular?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US")
    fun getPopularMovies(): Call<TMDbCallback>
}