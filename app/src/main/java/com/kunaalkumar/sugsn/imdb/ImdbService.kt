package com.kunaalkumar.sugsn.imdb

import io.reactivex.Observable
import retrofit2.http.GET

interface ImdbService {

    @GET("chart/moviemeter")
    fun getMostPopularMovies(): Observable<String>

    @GET("search/title/?title_type=feature")
    fun getDetailedPopularMovies(): Observable<String>

    @GET("search/title/?title_type=feature&num_votes=250000,&sort=user_rating&view=advanced")
    fun getDetailedTopRatedMovies(): Observable<String>
}