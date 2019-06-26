package com.kunaalkumar.sugsn.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunaalkumar.sugsn.imdb.MovieListItem
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object ImdbRepository {
    val TAG: String = "ImdbRepository"
    private val imdbService = RetrofitFactory.makeImdbRetrofitService()

    private val _topRatedMoviesList = MutableLiveData<String>()
    val topRatedMoviesList: LiveData<String>
        get() = _topRatedMoviesList

    fun getTopRatedMovies(compositeDisposable: CompositeDisposable): LiveData<String> {

        compositeDisposable.add(
            imdbService.getTopRatedMovies()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _topRatedMoviesList.postValue(it)
                }, {
                    Log.d(TAG, "Failed: $it")
                })
        )
//        imdbService.getTopRatedMovies().enqueue(object : Callback<String> {
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val doc = Jsoup.parse(response.body())
//                doc.select("tbody.lister-list").select("tr").forEachIndexed { index, listItem ->
//                    val movie = parseMovie(listItem)
//                    println("${index + 1} ${movie.title} : ${movie.rating} ------> ${movie.link}")
//                }
//            }
//        })
        return topRatedMoviesList
    }

    // Parse movie list jsoup element and return as MovieListItem
    private fun parseMovie(movie: Element): MovieListItem {
        return MovieListItem(
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