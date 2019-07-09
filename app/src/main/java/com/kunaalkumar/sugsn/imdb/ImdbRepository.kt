package com.kunaalkumar.sugsn.imdb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.sugsn.rotten.RottenRepository
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.*
import kotlin.collections.ArrayList

object ImdbRepository {
    val TAG: String = "ImdbRepository"
    private val imdbService = RetrofitFactory.makeImdbRetrofitService()

    fun getTopRatedMovies(): Flowable<ArrayList<ListItem>> {
        return imdbService.getTopRatedMovies().map { response ->
            val doc = Jsoup.parse(response)
            val listOfMovies = ArrayList<ListItem>()

            doc.select("tbody.lister-list").select("tr").forEach { listItem ->
                listOfMovies.add(
                    parseMovie(listItem)
                )
            }

            return@map listOfMovies
        }
    }

    // Parse movie list jsoup element and return as ListItem
    private fun parseMovie(movie: Element): ListItem {
        return ListItem(
            movie.selectFirst("td.titleColumn").selectFirst("a").text(),
            movie.selectFirst("td.posterColumn")
                .selectFirst("a")
                .selectFirst("img")
                .attr("src")
                .replace(".jpg", "#\$1.jpg"), // Replace to increase poster resolution
            movie.selectFirst("td.ratingColumn.imdbRating").selectFirst("strong").text(),
            movie.selectFirst("td.titleColumn").selectFirst("a").attr("href")
        )
    }
}