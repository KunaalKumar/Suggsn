package com.kunaalkumar.suggsn.taste_dive

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Reference: https://tastedive.com/read/api
 */
interface ITasteDiveService {

    @GET("similar/type=movies&info=1")
    fun getSimilarMovies(@Query("api_key") k: String) : Call<TDResult>
}