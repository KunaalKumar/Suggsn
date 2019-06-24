package com.kunaalkumar.sugsn.util

import com.kunaalkumar.sugsn.BuildConfig
import com.kunaalkumar.sugsn.imdb.ImdbService
import com.kunaalkumar.sugsn.tmdb.ITMDbService
import com.kunaalkumar.sugsn.trakt.ITraktService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitFactory {
    private const val TMDb_BASE_URL = "https://api.themoviedb.org/3/"
    private const val TRAKT_BASE_URL = "https://api.trakt.tv/"
    private const val IMDB_BASE_URL = "https://www.imdb.com/"

    fun makeTMDbRetrofitService(): ITMDbService {
        return Retrofit.Builder()
            .baseUrl(TMDb_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(ITMDbService::class.java)
    }

    fun makeTraktRetrofitService(): ITraktService {
        return Retrofit.Builder()
            .baseUrl(TRAKT_BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", BuildConfig.TRAKT_API_KEY)

                val req = requestBuilder.build()
                return@addInterceptor it.proceed(req)
            }.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(ITraktService::class.java)
    }

    fun makeImdbRetrofitService(): ImdbService {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(IMDB_BASE_URL)
            .client(OkHttpClient())
            .build().create(ImdbService::class.java)
    }

}