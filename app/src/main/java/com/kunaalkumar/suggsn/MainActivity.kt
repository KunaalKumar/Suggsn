package com.kunaalkumar.suggsn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitFactory.makeRetrofitService()
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getSimilarMovies("Drive")
            try {
                val response = request.await()
                text_view.text = response.body().toString()
            } catch (e: HttpException) {
                text_view.text = "Error"
            } catch (e: Throwable) {
                text_view.text = "Something went wrong"
            }
        }
    }
}
