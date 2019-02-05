package com.kunaalkumar.suggsn.taste_dive

import com.kunaalkumar.suggsn.BuildConfig
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Reference: https://tastedive.com/read/api
 */
interface ITasteDiveService {

    @GET("similar?k=${BuildConfig.TASTE_DIVE_API_KEY}&type=movies&info=1")
    fun getSimilarMovies(@Query("q") movie: String): Deferred<Response<TDResult>>
}