package com.kunaalkumar.sugsn.imdb

import com.kunaalkumar.sugsn.util.ListItem
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object ImdbRepository {

    val TAG: String = "ImdbRepository"
    private val imdbService = RetrofitFactory.makeImdbRetrofitService()

    fun getPopularMovies(): Observable<ArrayList<ListItem>> {
        return imdbService.getDetailedPopularMovies().map { response ->
            val doc = Jsoup.parse(response)
            val listOfMovies = ArrayList<ListItem>()

            doc.select("div.lister-list").select("div.lister-item.mode-advanced")
                .forEach { listItem ->
                    listOfMovies.add(parseMovieAdv(listItem))
                }
            return@map listOfMovies
        }.subscribeOn(Schedulers.io())
    }

    fun getDetailedTopRatedMovies(): Observable<ArrayList<ListItem>> {
        return imdbService.getDetailedTopRatedMovies().map { response ->
            val doc = Jsoup.parse(response)
            val listofMovies = ArrayList<ListItem>()
            doc.select("div.lister-list").select("div.lister-item.mode-advanced")
                .forEach { listItem ->
                    listofMovies.add(parseMovieAdv(listItem))
                }
            return@map listofMovies
        }.subscribeOn(Schedulers.io())
    }

    // Parse movie list jsoup element and return as ListItem
    private fun parseMovieAdv(movie: Element): ListItem {
        val ratingBar = movie.selectFirst("div.ratings-bar")
        var imdbRating: String? = null
        if (ratingBar != null) {
            imdbRating = ratingBar.selectFirst("strong")?.text()
        }

        return ListItem(
            movie.selectFirst("h3.lister-item-header > a").text(),
            movie.selectFirst("h3.lister-item-header > span.lister-item-year.text-muted.unbold").text().removeSurrounding(
                "(",
                ")"
            ),
            movie.selectFirst("div.lister-item-image.float-left > a > img").attr("loadlate")
                .replace(".jpg", "#\$1.jpg"), // Replace to increase poster resolution
            imdbRating,
            null,
            movie.selectFirst("h3.lister-item-header > a").attr("href")
        )
    }

}