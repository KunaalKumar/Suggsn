package com.kunaalkumar.sugsn.imdb

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ImdbService {

    @GET("movies-in-theaters/")
    fun getMoviesInTheaters(): Call<String>

    @GET("chart/top/")
    fun getTopRatedMovies(): Observable<String>

    @GET("chart/moviemeter")
    fun getMostPopularMovies(): Observable<String>

}