package com.kunaalkumar.sugsn.trakt

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITraktService {

    @GET("movies/trending")
    fun trendingMovies(@Query("page") page: Int): Call<List<TraktTrendingMovies>>
}