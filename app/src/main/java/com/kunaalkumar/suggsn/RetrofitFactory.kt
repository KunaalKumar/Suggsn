package com.kunaalkumar.suggsn

import com.kunaalkumar.suggsn.tmdb.ITMDbService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    private const val TMDb_BASE_URL = "https://api.themoviedb.org/3/"

    fun makeTMDbRetrofitService(): ITMDbService {
        return Retrofit.Builder()
            .baseUrl(TMDb_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(ITMDbService::class.java)
    }

}