package com.kunaalkumar.sugsn.imdb

import com.kunaalkumar.sugsn.util.ListItem
import com.kunaalkumar.sugsn.util.RetrofitFactory
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object ImdbRepository {

    val TAG: String = "ImdbRepository"
    private val imdbService = RetrofitFactory.makeImdbRetrofitService()

    suspend fun getPopularMovies(): ArrayList<ListItem> {
        val doc = Jsoup.parse(imdbService.getPopularMovies())
        val listOfMovies = ArrayList<ListItem>()

        doc.select("div.lister-list").select("div.lister-item.mode-advanced")
            .forEach { listItem ->
                listOfMovies.add(parseMovie(listItem))
            }
        return listOfMovies
    }

    suspend fun getTopRatedMovies(): ArrayList<ListItem> {
        val doc = Jsoup.parse(imdbService.getTopRatedMovies())
        val listOfMovies = ArrayList<ListItem>()
        doc.select("div.lister-list").select("div.lister-item.mode-advanced")
            .forEach { listItem ->
                listOfMovies.add(parseMovie(listItem))
            }
        return listOfMovies
    }

    // Parse movie list jsoup element and return as ListItem
    private fun parseMovie(movie: Element): ListItem {
        val ratingBar = movie.selectFirst("div.ratings-bar")
        var imdbRating: String? = null
        if (ratingBar != null) {
            imdbRating = ratingBar.selectFirst("strong")?.text()
        }

        return ListItem(
            movie.selectFirst("h3.lister-item-header > a").text(),
            "[0-9]{4}".toRegex().find(movie.selectFirst("h3.lister-item-header > span.lister-item-year.text-muted.unbold").text())?.groups?.get(
                0
            )?.value,
            movie.selectFirst("div.lister-item-image.float-left > a > img").attr("loadlate")
                .replace(".jpg", "#\$1.jpg"), // Replace to increase poster resolution
            imdbRating,
            null,
            movie.selectFirst("h3.lister-item-header > a").attr("href")
        )
    }

}