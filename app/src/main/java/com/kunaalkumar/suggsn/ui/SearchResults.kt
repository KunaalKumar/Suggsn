package com.kunaalkumar.suggsn.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.taste_dive.RetrofitFactory
import kotlinx.android.synthetic.main.fragment_search_results.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchResults : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        search_button.setOnClickListener {
            val service = RetrofitFactory.makeRetrofitService()
            GlobalScope.launch(Dispatchers.Main) {
                val request = service.getSimilarMovies(search_edit_text.text.toString())
                try {
                    val response = request.await()
                    Toast.makeText(context, response.body()!!.Similar.Results[0].Name, Toast.LENGTH_LONG).show()
                } catch (e: Throwable) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
