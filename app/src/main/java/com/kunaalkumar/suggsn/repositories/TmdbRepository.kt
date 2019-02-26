package com.kunaalkumar.suggsn.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.suggsn.RetrofitFactory
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbConfigCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Singleton class to get data from TMDB
 */
class TmdbRepository private constructor() {

    val TAG: String = "Suggsn@TmdbRepository"
    private val tmdbService = RetrofitFactory.makeTMDbRetrofitService()
    private var isConfigured = false

    companion object {
        val instance: TmdbRepository by lazy { TmdbRepository() }
        var BASE_IMAGE_URL: String = ""
        var BASE_POSTER_SIZE: String = ""
        var BASE_BACKDROP_SIZE: String = ""
        var WIDTH: Int = 0
        var HEIGHT: Int = 0
        const val DISCOVER_MOVIE = "MOVIE"
        const val DISCOVER_TV = "TV"
        const val MOVIES_POPULAR = "MOVIE_POPULAR"
        const val MOVIES_TOP_RATED = "MOVIES_TOP_RATED"
        const val MOVIES_UPCOMING = "MOVIES_UPCOMING"
        const val MOVIES_NOW_PLAYING = "MOVIES_NOW_PLAYING"
        const val SHOWS_POPULAR = "SHOWS_POPULAR"
        const val SHOWS_TOP_RATED = "SHOWS_TOP_RATED"
        const val SHOWS_ON_AIR = "SHOWS_ON_AIR"
        const val SHOWS_AIRING_TODAY = "SHOWS_AIRING_TODAY"
    }

    init {
        tmdbConfig()
    }

    fun getConfigStatus(): LiveData<Boolean> {
        val retval = MutableLiveData<Boolean>()
        retval.value = isConfigured
        return retval
    }

    // Fetch configurations for image paths
    private fun tmdbConfig() {
        tmdbService.config().enqueue(object : Callback<TMDbConfigCallback> {

            override fun onResponse(call: Call<TMDbConfigCallback>, response: Response<TMDbConfigCallback>) {
                BASE_IMAGE_URL = response.body()!!.images.base_url
                BASE_POSTER_SIZE = response.body()!!.images.poster_sizes[response.body()!!.images.poster_sizes.size - 1]
                BASE_BACKDROP_SIZE =
                    response.body()!!.images.backdrop_sizes[response.body()!!.images.backdrop_sizes.size - 1]
                isConfigured = true
                android.util.Log.d(TAG, "tmdbConfig: Configured URLs")
            }

            override fun onFailure(call: Call<TMDbConfigCallback>, t: Throwable) {
                android.util.Log.d(TAG, "tmdbConfig: Something went wrong \n$t\n")
            }
        })
    }

    // Fetch and load a random poster for a popular movie
    fun getBackdropUrl(): MutableLiveData<String> {

        val data = MutableLiveData<String>()

        tmdbService.popularMovies(1).enqueue(object : Callback<TMDbCallback> {

            override fun onResponse(call: Call<TMDbCallback>, response: Response<TMDbCallback>) {
                val randomImageNum = (0..19).random()
                val imageUrl = response.body()!!.results[randomImageNum].getPoster().toString()
                data.value = imageUrl
            }

            override fun onFailure(call: Call<TMDbCallback>, t: Throwable) {
                android.util.Log.d(TAG, "getBackdropUrl: Something went wrong \n$t\n")
            }
        })

        return data
    }

    /**
     * Search movies, tv shows, people, etc for given query
     */
    fun getSearchResults(query: String, pageNum: Int, includeAdult: Boolean)
            : MutableLiveData<TMDbCallback> {

        val data = MutableLiveData<TMDbCallback>()

        tmdbService.searchMulti(query, pageNum, includeAdult).enqueue(object : Callback<TMDbCallback> {
            override fun onResponse(call: Call<TMDbCallback>, response: Response<TMDbCallback>) {
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<TMDbCallback>, t: Throwable) {
                Log.e(TAG, "getSearchResults: Something went wrong \n$t\n")
            }
        })
        return data
    }

    /**
     * Types must be chosen from public tags provided in this class
     * Types: 1) Movie
     *        2) TV Shows
     */
    fun getDiscover(type: String, pageNum: Int): MutableLiveData<TMDbCallback> {
        val data = MutableLiveData<TMDbCallback>()

        when (type) {
            DISCOVER_MOVIE -> {
                tmdbService.discoverMovies(pageNum).enqueue(object : Callback<TMDbCallback> {
                    override fun onResponse(call: Call<TMDbCallback>, response: Response<TMDbCallback>) {
                        data.postValue(response.body())
                    }

                    override fun onFailure(call: Call<TMDbCallback>, t: Throwable) {
                        Log.e(TAG, "getDiscover: Something went wrong \n$t\n")
                    }
                })
            }
            DISCOVER_TV -> {
                tmdbService.discoverTVShows(pageNum).enqueue(object : Callback<TMDbCallback> {
                    override fun onResponse(call: Call<TMDbCallback>, response: Response<TMDbCallback>) {
                        data.postValue(response.body())
                    }

                    override fun onFailure(call: Call<TMDbCallback>, t: Throwable) {
                        Log.e(TAG, "getDiscover: Something went wrong \n$t\n")
                    }
                })
            }
        }
        return data
    }

    fun getMovies(type: String, pageNum: Int): MutableLiveData<TMDbCallback> {
        val data = MutableLiveData<TMDbCallback>()

        when (type) {
            MOVIES_POPULAR ->
                tmdbService.popularMovies(pageNum).enqueue(ApiCallback(data))

            MOVIES_TOP_RATED ->
                tmdbService.topRatedMovies(pageNum).enqueue(ApiCallback(data))

            MOVIES_UPCOMING ->
                tmdbService.upcomingMovies(pageNum).enqueue(ApiCallback(data))

            MOVIES_NOW_PLAYING ->
                tmdbService.nowPlayingMovies(pageNum).enqueue(ApiCallback(data))
        }
        return data
    }

    fun getShows(type: String, pageNum: Int): MutableLiveData<TMDbCallback> {
        val data = MutableLiveData<TMDbCallback>()

        when (type) {
            SHOWS_POPULAR ->
                tmdbService.popularShows(pageNum).enqueue(ApiCallback(data))
            SHOWS_TOP_RATED ->
                tmdbService.topRatedShows(pageNum).enqueue(ApiCallback(data))
            SHOWS_ON_AIR ->
                tmdbService.onAirShows(pageNum).enqueue(ApiCallback(data))
            SHOWS_AIRING_TODAY ->
                tmdbService.airingTodayShows(pageNum).enqueue(ApiCallback(data))
        }
        return data
    }

    private class ApiCallback(val data: MutableLiveData<TMDbCallback>) : Callback<TMDbCallback> {
        val TAG: String = "Suggsn@apiCallback"
        override fun onResponse(call: Call<TMDbCallback>, response: Response<TMDbCallback>) {
            data.postValue(response.body())
        }

        override fun onFailure(call: Call<TMDbCallback>, t: Throwable) {
            Log.e(TAG, "getMovies: Something went wrong \n$t\n")
        }
    }

}