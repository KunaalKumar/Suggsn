package com.kunaalkumar.sugsn

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kunaalkumar.sugsn.home.HomeFragment
import com.kunaalkumar.sugsn.imdb.ImdbService
import com.kunaalkumar.sugsn.movies.MoviesFragment
import com.kunaalkumar.sugsn.people.PeopleFragment
import com.kunaalkumar.sugsn.repositories.TmdbRepository
import com.kunaalkumar.sugsn.shows.ShowsFragment
import com.kunaalkumar.sugsn.util.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get screen size and convert to pixels from dpi for poster images
        val displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        TmdbRepository.WIDTH = (displayMetric.widthPixels * 0.5).roundToInt()
        TmdbRepository.HEIGHT = (TmdbRepository.WIDTH * 1.5).roundToInt()

        /*****/

        val imdbService = RetrofitFactory.makeImdbRetrofitService()

        imdbService.getMoviesInTheaters().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val doc = Jsoup.parse(response.body())
                Log.d(TAG, "Response body: ${doc.getElementsByClass("list detail sub-list")[0]
                    .getElementsByAttributeValue("itemType","http://schema.org/Movie")[0]
                    .getElementsByClass("overview-top")[0]
                    .getElementsByTag("a")[0]
                    .attr("title")}")
            }
        })

        /********/
//        openFragment(HomeFragment())

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_dest -> {
                    //openFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.movies_dest -> {
                    //openFragment(MoviesFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.shows_dest -> {
                    //openFragment(ShowsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.people_dest -> {
                    //openFragment(PeopleFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).disallowAddToBackStack()
        transaction.commit()
    }
}
