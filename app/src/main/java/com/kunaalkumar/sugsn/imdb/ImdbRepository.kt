package com.kunaalkumar.sugsn.imdb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import kotlin.collections.ArrayList

object ImdbRepository {
    val TAG: String = "ImdbRepository"
    private val imdbService = RetrofitFactory.makeImdbRetrofitService()

    private val _topRatedMoviesList = MutableLiveData<ArrayList<ImdbListItem>>()
    private val topRatedMoviesList: LiveData<ArrayList<ImdbListItem>>
        get() = _topRatedMoviesList

    fun getTopRatedMovies(compositeDisposable: CompositeDisposable): LiveData<ArrayList<ImdbListItem>> {

        compositeDisposable.add(
            imdbService.getTopRatedMovies()
                .map { response ->
                    // Create list of ImdbListItems from response
                    val doc = Jsoup.parse(response)
                    val listOfMovies = ArrayList<ImdbListItem>()
                    doc.select("tbody.lister-list").select("tr").forEach { listItem ->
                        listOfMovies.add(
                            parseMovie(
                                listItem
                            )
                        )
                    }
                    return@map listOfMovies
                }
                .subscribeOn(Schedulers.io())
                .subscribe({ movieList ->
                    _topRatedMoviesList.postValue(movieList)
                }, {
                    Log.d(TAG, "Failed: $it")
                })
        )
        return topRatedMoviesList
    }

    // Parse movie list jsoup element and return as ImdbListItem
    private fun parseMovie(movie: Element): ImdbListItem {
        return ImdbListItem(
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