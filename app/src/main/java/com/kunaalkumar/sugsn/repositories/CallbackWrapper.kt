package com.kunaalkumar.sugsn.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Standard ApiCallback wrapper class
class CallbackWrapper<T>(val data: MutableLiveData<T>) : Callback<T> {
    val TAG: String = "CallbackWrapper"
    override fun onResponse(call: Call<T>, response: Response<T>) {
        data.postValue(response.body())
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "CallbackWrapper: Something went wrong \n$t\n")
    }
}