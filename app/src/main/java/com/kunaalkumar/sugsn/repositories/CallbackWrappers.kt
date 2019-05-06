package com.kunaalkumar.sugsn.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.sugsn.trakt.TraktResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TRAKT_CURRENT_PAGE = "X-Pagination-Page"
private val TRAKT_PAGE_LIMIT = "X-Pagination-Limit"
private val TRAKT_MAX_PAGE = "X-Pagination-Page-Count"

// Standard ApiCallback wrapper class
class TMDbCallbackWrapper<T>(val data: MutableLiveData<T>) : Callback<T> {
    val TAG: String = "TMDbCallbackWrapper"
    override fun onResponse(call: Call<T>, response: Response<T>) {
        data.postValue(response.body())
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "Something went wrong \n$t\n")
    }
}

class TraktCallbackWrapper<T>(val data: MutableLiveData<TraktResponse<T>>) : Callback<T> {
    val TAG: String = "TraktCallbackWrapper"
    override fun onResponse(call: Call<T>, response: Response<T>) {
        data.postValue(
            TraktResponse(
                response.body()!!,
                response.headers().get(TRAKT_CURRENT_PAGE)!!.toInt(),
                response.headers().get(TRAKT_PAGE_LIMIT)!!.toInt(),
                response.headers().get(TRAKT_MAX_PAGE)!!.toInt()
            )
        )
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "Something went wrong \n$t\n")
    }
}