package com.kunaalkumar.suggsn.repositories

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

        tmdbService.popularMovies().enqueue(object : Callback<TMDbCallback> {

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
                android.util.Log.d(TAG, "getSearchResults: Something went wrong \n$t\n")
            }
        })
        return data
    }

    fun getDiscoverMovies(): MutableLiveData<TMDbCallback> {
        val data = MutableLiveData<TMDbCallback>()

        tmdbService.discoverMovies().enqueue(object : Callback<TMDbCallback> {
            override fun onResponse(call: Call<TMDbCallback>, response: Response<TMDbCallback>) {
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<TMDbCallback>, t: Throwable) {
                android.util.Log.d(TAG, "getDiscoverMovies: Something went wrong \n$t\n")
            }
        })
        return data
    }


}