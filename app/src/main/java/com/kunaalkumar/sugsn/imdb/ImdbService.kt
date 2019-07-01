package com.kunaalkumar.sugsn.imdb

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET

interface ImdbService {

    @GET(".")
    fun getResponse(): Call<String>

    @GET("movies-in-theaters/")
    fun getMoviesInTheaters(): Call<String>

    @GET("chart/top/")
    fun getTopRatedMovies(): Flowable<String>

}