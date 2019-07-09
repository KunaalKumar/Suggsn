package com.kunaalkumar.sugsn.rotten

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRottenService {

    @GET("/tv/{name}")
    fun getTV(@Path("name") name: String): Call<String>

    @GET("/search/")
    fun getSearchResults(@Query("search") query: String): Flowable<String>
}