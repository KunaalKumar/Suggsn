package com.kunaalkumar.suggsn.tmdb

import com.kunaalkumar.suggsn.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Reference: https://developers.themoviedb.org/3
 */
interface ITMDbService {

    @GET("search/multi?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun searchMulti(
        @Query("query") query: String, @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean
    ): Call<TMDbCallback>

    @GET("configuration?api_key=${BuildConfig.TMDb_API_KEY}")
    fun config(): Call<TMDbConfigCallback>

    @GET("discover/movie?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&primary_release_year=2018&region=US")
    fun discoverMovies(@Query("page") page: Int): Call<TMDbCallback>

    @GET("discover/tv?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun discoverTVShows(@Query("page") page: Int): Call<TMDbCallback>

    @GET("movie/popular?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun popularMovies(@Query("page") page: Int): Call<TMDbCallback>

    @GET("movie/top_rated?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun topRatedMovies(@Query("page") page: Int): Call<TMDbCallback>

    @GET("movie/upcoming?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun upcomingMovies(@Query("page") page: Int): Call<TMDbCallback>

    @GET("movie/now_playing?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun nowPlayingMovies(@Query("page") page: Int): Call<TMDbCallback>

    @GET("tv/popular?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun popularShows(@Query("page") page: Int): Call<TMDbCallback>

    @GET("tv/top_rated?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun topRatedShows(@Query("page") page: Int): Call<TMDbCallback>

    @GET("tv/on_the_air?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun onAirShows(@Query("page") page: Int): Call<TMDbCallback>

    @GET("tv/airing_today?api_key=${BuildConfig.TMDb_API_KEY}&language=en-US&region=US")
    fun airingTodayShows(@Query("page") page: Int): Call<TMDbCallback>
}