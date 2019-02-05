package com.kunaalkumar.suggsn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kunaalkumar.suggsn.taste_dive.ITasteDiveService
import com.kunaalkumar.suggsn.taste_dive.TDResult
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitClientInstance.retrofitInstance?.create(ITasteDiveService::class.java)
        val call = service?.getSimilarMovies("Drive")
        call?.enqueue(object : Callback<TDResult> {
            override fun onFailure(call: Call<TDResult>, t: Throwable) {
                text_view.text = "Failed call"
            }

            override fun onResponse(call: Call<TDResult>, response: Response<TDResult>) {
                text_view.text = "Success!"
            }

        })
    }
}
