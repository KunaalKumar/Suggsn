package com.kunaalkumar.sugsn.rotten

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRottenService {

    @GET("/tv/{name}")
    fun getTV(@Path("name") name: String): Call<String>

    @GET("/m/{name}")
    fun getMovie(@Path("name") name: String): Observable<String>

    @GET("/search/")
    fun getSearchResults(@Query("search") query: String): Observable<String>

    @GET("/search/")
    fun testSR(@Query("search") query: String): Call<String>

}