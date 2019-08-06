package com.kunaalkumar.sugsn.util

import com.kunaalkumar.sugsn.imdb.ImdbService
import com.kunaalkumar.sugsn.omdb.IOMDBService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitFactory {
    private const val IMDB_BASE_URL = "https://www.imdb.com/"
    private const val OMDB_BASE_URL = "https://www.omdbapi.com/"

    fun makeImdbRetrofitService(): ImdbService =
        Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(IMDB_BASE_URL)
            .client(OkHttpClient())
            .build().create(ImdbService::class.java)

    fun makeOMDBRetrofitService(): IOMDBService =
        Retrofit.Builder()
            .baseUrl(OMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(IOMDBService::class.java)

}