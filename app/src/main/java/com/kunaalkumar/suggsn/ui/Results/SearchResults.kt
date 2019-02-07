package com.kunaalkumar.suggsn.ui.Results


import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.suggsn.R
import com.kunaalkumar.suggsn.RetrofitFactory
import com.kunaalkumar.suggsn.tmdb.TMDbItem
import kotlinx.android.synthetic.main.fragment_search_results.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchResults : Fragment() {

    private val TAG: String = "SearchResults"
    private val constraintSetOld = ConstraintSet()
    private val constraintSetNew = ConstraintSet()

    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: ResultsAdapter
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        constraintSetOld.clone(fragment_search_results_layout)
        constraintSetNew.clone(context, R.layout.fragment_search_results_alt)

        search_button.setOnClickListener {
            TransitionManager.beginDelayedTransition(fragment_search_results_layout)
            constraintSetNew.applyTo(fragment_search_results_layout)
            search_edit_text.background = null
            search_edit_text.setTextColor(ContextCompat.getColor(context!!, R.color.colorAccentDark))
            searchInProgress(true)

            val service = RetrofitFactory.makeTMDbRetrofitService()
            GlobalScope.launch(Dispatchers.Main) {
                // TODO: Change page and includeAdult fields based on UI selection
                val request = service.searchMulti(search_edit_text.text.toString(), 1, false)
                try {
                    val response = request.await()
                    searchInProgress(false)
                    initRecyclerView(response.body()!!.results)
                } catch (e: Throwable) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initRecyclerView(dataSet: List<TMDbItem>) {
        viewManager = LinearLayoutManager(context)
        viewAdapter = ResultsAdapter(dataSet)
        recyclerView = recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        Log.d(TAG, "initRecyclerView: Successfully initialized recycler view and data set size ${dataSet.size}")
    }

    // Sets UI elements accordingly to bool argument
    private fun searchInProgress(inProgress: Boolean) {
        search_button.isEnabled = !inProgress
        search_edit_text.isEnabled = !inProgress
    }
}
