package com.kunaalkumar.sugsn.imdb

import retrofit2.Call
import retrofit2.http.GET

interface ImdbService {

    @GET(".")
    fun getResponse(): Call<String>

    @GET("movies-in-theaters/")
    fun getMoviesInTheaters(): Call<String>

}