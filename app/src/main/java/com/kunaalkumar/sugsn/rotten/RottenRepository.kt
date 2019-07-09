package com.kunaalkumar.sugsn.rotten

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.sugsn.imdb.ListItem
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

fun main() {
    RottenRepository.getTV("the boys")
}

object RottenRepository {
    val TAG: String = "RottenRepository"
    private val rottenService = RetrofitFactory.makeRottenRetrofitService()

    fun getTV(name: String) {
        val showName = name.replace(" ", "_").toLowerCase(Locale.getDefault())
        println("Name is: $showName")
        rottenService.getTV(showName).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() == null) {
                    // retry with year in parameter
                    return
                }
                val doc = Jsoup.parse(response.body())
                val scores = doc.select("span.mop-ratings-wrap__percentage")
                println("RT: ${scores[0].text()}")
                if (scores.size > 1)
                    println("Audience: ${scores[1].text()}")
            }
        })
    }

    fun getSearchResult(query: String, compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(
            rottenService.getSearchResults(query).map { response ->
                Log.d(TAG, "Responded with: $response")
                return@map response
            }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "Test for : $query")
                }, {
                })
        )
    }
}