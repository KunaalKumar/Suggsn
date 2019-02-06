package com.kunaalkumar.suggsn.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kunaalkumar.suggsn.taste_dive.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SearchResults.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SearchResults.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SearchResults : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = RetrofitFactory.makeRetrofitService()
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getSimilarMovies("Drive")
            try {
                val response = request.await()
                Toast.makeText(context, response.body()!!.Similar.Results[0].Name, Toast.LENGTH_LONG).show()
            } catch (e: Throwable) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }
}
