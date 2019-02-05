package com.kunaalkumar.suggsn.taste_dive

import com.kunaalkumar.suggsn.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Reference: https://tastedive.com/read/api
 */
interface ITasteDiveService {

    @GET("similar/type=movies&info=1&k=${BuildConfig.TASTE_DIVE_API_KEY}")
    fun getSimilarMovies(@Query("q") movie: String) : Call<TDResult>
}