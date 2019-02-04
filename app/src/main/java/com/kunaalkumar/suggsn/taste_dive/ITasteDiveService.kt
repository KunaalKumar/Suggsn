package com.kunaalkumar.suggsn.taste_dive

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITasteDiveService {

    @GET("similar/type=movies")
    fun getSimilarMovies(@Query("api_key") k: String) : Call<TDResult>
}