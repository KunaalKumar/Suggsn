package com.kunaalkumar.sugsn.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.sugsn.tmdb.*
import com.kunaalkumar.sugsn.util.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Singleton class to get data from TMDB
 */
object TmdbRepository {

    private val TAG: String = "TmdbRepository"
    private val tmdbService = RetrofitFactory.makeTMDbRetrofitService()
    private var isConfigured = false

    var BASE_IMAGE_URL: String = ""
    var BASE_POSTER_SIZE: String = ""
    var BASE_BACKDROP_SIZE: String = ""
    var WIDTH: Int = 0
    var HEIGHT: Int = 0
    const val DISCOVER_MOVIE = "MOVIES"
    const val DISCOVER_TV = "TV"
    const val MOVIES_POPULAR = "MOVIE_POPULAR"
    const val MOVIES_TOP_RATED = "MOVIES_TOP_RATED"
    const val MOVIES_UPCOMING = "MOVIES_UPCOMING"
    const val MOVIES_NOW_PLAYING = "MOVIES_NOW_PLAYING"
    const val SHOWS_POPULAR = "SHOWS_POPULAR"
    const val SHOWS_TOP_RATED = "SHOWS_TOP_RATED"
    const val SHOWS_ON_AIR = "SHOWS_ON_AIR"
    const val SHOWS_AIRING_TODAY = "SHOWS_AIRING_TODAY"

    init {
        // Fetch configurations for image paths
        tmdbService.config().enqueue(object : Callback<TMDbConfigCallback> {

            override fun onResponse(call: Call<TMDbConfigCallback>, response: Response<TMDbConfigCallback>) {
                BASE_IMAGE_URL = response.body()!!.images.base_url
                BASE_POSTER_SIZE = response.body()!!.images.poster_sizes[response.body()!!.images.poster_sizes.size - 1]
                BASE_BACKDROP_SIZE =
                    response.body()!!.images.backdrop_sizes[response.body()!!.images.backdrop_sizes.size - 1]
                isConfigured = true
                Log.d(TAG, "tmdbConfig: Configured URLs")
            }

            override fun onFailure(call: Call<TMDbConfigCallback>, t: Throwable) {
                Log.d(TAG, "tmdbConfig: Something went wrong \n$t\n")
            }
        })
    }

    fun getConfigStatus(): LiveData<Boolean> {
        val retval = MutableLiveData<Boolean>()
        retval.value = isConfigured
        return retval
    }

    // Fetch and load a random poster for a popular movie
    fun getBackdropUrl(): MutableLiveData<String> {

        val data = MutableLiveData<String>()

        tmdbService.popularMovies(1).enqueue(object : Callback<TMDbCallback<TMDbItem>> {

            override fun onResponse(call: Call<TMDbCallback<TMDbItem>>, response: Response<TMDbCallback<TMDbItem>>) {
                val randomImageNum = (0..19).random()
                val imageUrl = response.body()!!.results[randomImageNum].getPoster().toString()
                data.value = imageUrl
            }

            override fun onFailure(call: Call<TMDbCallback<TMDbItem>>, t: Throwable) {
                android.util.Log.d(TAG, "getBackdropUrl: Something went wrong \n$t\n")
            }
        })

        return data
    }

    /**
     * Search movies, tv shows, people, etc for given query
     */
    fun getSearchResults(query: String, pageNum: Int, includeAdult: Boolean)
            : MutableLiveData<TMDbCallback<TMDbItem>> {

        val data = MutableLiveData<TMDbCallback<TMDbItem>>()

        tmdbService.searchMulti(query, pageNum, includeAdult).enqueue(object : Callback<TMDbCallback<TMDbItem>> {
            override fun onResponse(call: Call<TMDbCallback<TMDbItem>>, response: Response<TMDbCallback<TMDbItem>>) {
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<TMDbCallback<TMDbItem>>, t: Throwable) {
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
    fun getDiscover(type: String, pageNum: Int): MutableLiveData<TMDbCallback<TMDbItem>> {
        val data = MutableLiveData<TMDbCallback<TMDbItem>>()

        when (type) {
            DISCOVER_MOVIE -> {
                tmdbService.discoverMovies(pageNum).enqueue(object : Callback<TMDbCallback<TMDbItem>> {
                    override fun onResponse(
                        call: Call<TMDbCallback<TMDbItem>>,
                        response: Response<TMDbCallback<TMDbItem>>
                    ) {
                        data.postValue(response.body())
                    }

                    override fun onFailure(call: Call<TMDbCallback<TMDbItem>>, t: Throwable) {
                        Log.e(TAG, "getTrending: Something went wrong \n$t\n")
                    }
                })
            }
            DISCOVER_TV -> {
                tmdbService.discoverTVShows(pageNum).enqueue(object : Callback<TMDbCallback<TMDbItem>> {
                    override fun onResponse(
                        call: Call<TMDbCallback<TMDbItem>>,
                        response: Response<TMDbCallback<TMDbItem>>
                    ) {
                        data.postValue(response.body())
                    }

                    override fun onFailure(call: Call<TMDbCallback<TMDbItem>>, t: Throwable) {
                        Log.e(TAG, "getTrending: Something went wrong \n$t\n")
                    }
                })
            }
        }
        return data
    }

    fun getMovies(type: String, pageNum: Int): MutableLiveData<TMDbCallback<TMDbItem>> {
        val data = MutableLiveData<TMDbCallback<TMDbItem>>()

        when (type) {
            MOVIES_POPULAR ->
                tmdbService.popularMovies(pageNum).enqueue(TMDbCallbackWrapper(data))

            MOVIES_TOP_RATED ->
                tmdbService.topRatedMovies(pageNum).enqueue(TMDbCallbackWrapper(data))

            MOVIES_UPCOMING ->
                tmdbService.upcomingMovies(pageNum).enqueue(TMDbCallbackWrapper(data))

            MOVIES_NOW_PLAYING ->
                tmdbService.nowPlayingMovies(pageNum).enqueue(TMDbCallbackWrapper(data))
        }
        return data
    }

    fun getShows(type: String, pageNum: Int): MutableLiveData<TMDbCallback<TMDbItem>> {
        val data = MutableLiveData<TMDbCallback<TMDbItem>>()

        when (type) {
            SHOWS_POPULAR ->
                tmdbService.popularShows(pageNum).enqueue(TMDbCallbackWrapper(data))
            SHOWS_TOP_RATED ->
                tmdbService.topRatedShows(pageNum).enqueue(TMDbCallbackWrapper(data))
            SHOWS_ON_AIR ->
                tmdbService.onAirShows(pageNum).enqueue(TMDbCallbackWrapper(data))
            SHOWS_AIRING_TODAY ->
                tmdbService.airingTodayShows(pageNum).enqueue(TMDbCallbackWrapper(data))
        }
        return data
    }

    fun getPopularPeople(pageNum: Int): MutableLiveData<TMDbCallback<TMDbItem>> {
        val data = MutableLiveData<TMDbCallback<TMDbItem>>()
        tmdbService.popularPeople(pageNum).enqueue(TMDbCallbackWrapper(data))
        return data
    }

    // Get movie details by id
    fun getMovieDetails(id: Int): MutableLiveData<TMDbMovieDetails> {
        val data = MutableLiveData<TMDbMovieDetails>()
        tmdbService.movieDetails(id).enqueue(TMDbCallbackWrapper(data))
        return data
    }

    // Get videos related to movie
    fun getMovieVideos(id: Int): MutableLiveData<TMDbVideos> {
        val data = MutableLiveData<TMDbVideos>()
        tmdbService.movieVideos(id).enqueue(TMDbCallbackWrapper(data))
        return data
    }

}