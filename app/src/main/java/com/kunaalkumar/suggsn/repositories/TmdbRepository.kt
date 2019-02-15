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

    val TAG: String = "TmdbRepository"
    private val tmdbService = RetrofitFactory.makeTMDbRetrofitService()

    lateinit var searchResults: ArrayList<TMDbItem>


    companion object {
        val instance = TmdbRepository()
        lateinit var BASE_IMAGE_URL: String
        lateinit var BASE_POSTER_SIZE: String
        lateinit var BASE_BACKDROP_SIZE: String
    }

    init {
        tmdbConfig()
    }

    fun getSearchResults(query: String, pageNum: Int, includeAdult: Boolean)
            : MutableLiveData<ArrayList<TMDbItem>> {
//        tmdbMultiSearch(query, pageNum, includeAdult)
        val retval = MutableLiveData<ArrayList<TMDbItem>>()
        retval.value = searchResults
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
//    private fun tmdbMultiSearch(query: String, pageNum: Int, includeAdult: Boolean) {
//        GlobalScope.launch(Dispatchers.Main) {
//            // TODO: Change page and includeAdult fields based on UI selection
//            val request = tmdbService.searchMulti(query, pageNum, includeAdult)
//            try {
//                val response = request.await()
////                searchInProgress(false)
////                initRecyclerView(response.body()!!.results)
//                if (pageNum != 1) { // Append data to searchResults
//                    searchResults!!.addAll(response.body()!!.results)
//                    return@launch
//                }
//                searchResults = ArrayList(response.body()!!.results)
//            } catch (e: Throwable) {
////                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
//                Log.d(TAG, "(tmdbMultiSearch) Something went wrong: $e")
//            }
//        }
//    }


}