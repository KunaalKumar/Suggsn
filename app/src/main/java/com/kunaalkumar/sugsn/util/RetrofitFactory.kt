package com.kunaalkumar.sugsn.util

import com.kunaalkumar.sugsn.imdb.ImdbService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitFactory {
    private const val IMDB_BASE_URL = "https://www.imdb.com/"


    fun makeImdbRetrofitService(): ImdbService {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(IMDB_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient())
            .build().create(ImdbService::class.java)
    }

}