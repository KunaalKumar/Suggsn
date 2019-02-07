package com.kunaalkumar.suggsn

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kunaalkumar.suggsn.tmdb.ITMDbService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    private const val TMDb_BASE_URL = "https://api.themoviedb.org/3/"

    fun makeTMDbRetrofitService(): ITMDbService {
        return Retrofit.Builder()
            .baseUrl(TMDb_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ITMDbService::class.java)
    }

}