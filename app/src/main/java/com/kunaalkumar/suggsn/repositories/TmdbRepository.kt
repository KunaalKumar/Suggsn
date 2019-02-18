package com.kunaalkumar.suggsn.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.suggsn.RetrofitFactory
import com.kunaalkumar.suggsn.tmdb.TMDbCallback
import com.kunaalkumar.suggsn.tmdb.TMDbConfigCallback
import com.kunaalkumar.suggsn.tmdb.TMDbItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Singleton class to get data from TMDB
 */
class TmdbRepository {

    val TAG: String = "Suggsn@TmdbRepository"
    private val tmdbService = RetrofitFactory.makeTMDbRetrofitService()
    private var isConfigured = false

    companion object {
        val instance = TmdbRepository()
        lateinit var BASE_IMAGE_URL: String
        lateinit var BASE_POSTER_SIZE: String
        lateinit var BASE_BACKDROP_SIZE: String
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
            }

            override fun onFailure(call: Call<TMDbConfigCallback>, t: Throwable) {
                android.util.Log.d(TAG, "tmdbConfig: Something went wrong \n$t\n")
            }
        })
    }

    // Fetch and load a random poster for a popular movie
    fun getBackdropUrl(): LiveData<String> {

        val data = MutableLiveData<String>()

        tmdbService.getPopularMovies().enqueue(object : Callback<TMDbCallback> {

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
            : LiveData<ArrayList<TMDbItem>> {

        val data = MutableLiveData<ArrayList<TMDbItem>>()

        tmdbService.searchMulti(query, pageNum, includeAdult).enqueue(object : Callback<TMDbCallback> {
            override fun onResponse(call: Call<TMDbCallback>, response: Response<TMDbCallback>) {
                data.value = ArrayList(response.body()!!.results)
            }

            override fun onFailure(call: Call<TMDbCallback>, t: Throwable) {
                android.util.Log.d(TAG, "getSearchResults: Something went wrong \n$t\n")
            }
        })
        return data
    }


}