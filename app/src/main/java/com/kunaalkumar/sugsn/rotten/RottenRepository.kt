package com.kunaalkumar.sugsn.rotten

import android.util.Log
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.Observable
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object RottenRepository {

    enum class SearchType {
        MOVIE, TV
    }

    private val TAG: String = "RottenRepository"
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

    fun getMovie(name: String): Observable<String> {
        return rottenService.getMovie(name).map { response ->
            val doc = Jsoup.parse(response)
            doc.select("span.mop-ratings-wrap__percentage").first().text()
            return@map "10"
        }
    }

    fun getSearchResult(query: String, searchType: SearchType): Observable<String> {
        return rottenService.getSearchResults(query).map { response ->
            val doc = Jsoup.parse(response)
            when (searchType) {
                RottenRepository.SearchType.MOVIE -> {
                    val test = doc.select("ul.results_ul")
                    println("Got this ${test.size}")
                }
                RottenRepository.SearchType.TV -> {
                }
            }
            Log.d(TAG, "Responded with: $response")
            return@map response
        }
    }
}