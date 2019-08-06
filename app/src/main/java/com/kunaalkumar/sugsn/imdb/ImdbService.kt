package com.kunaalkumar.sugsn.imdb

import retrofit2.http.GET

interface ImdbService {

    @GET("chart/moviemeter")
    suspend fun getMostPopularMovies(): String

    @GET("search/title/?title_type=feature")
    suspend fun getPopularMovies(): String

    @GET("search/title/?title_type=feature&num_votes=250000,&sort=user_rating&view=advanced")
    suspend fun getTopRatedMovies(): String
}