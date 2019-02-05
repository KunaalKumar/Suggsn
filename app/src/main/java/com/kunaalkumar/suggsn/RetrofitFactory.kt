package com.kunaalkumar.suggsn

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kunaalkumar.suggsn.taste_dive.ITasteDiveService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    private const val BASE_URL = "https://tastedive.com/api/"

    fun makeRetrofitService(): ITasteDiveService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ITasteDiveService::class.java)
    }
}