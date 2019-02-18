//package com.kunaalkumar.suggsn.results
//
//
//import android.animation.ObjectAnimator
//import android.os.Bundle
//import android.transition.TransitionManager
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.constraintlayout.widget.ConstraintSet
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.textfield.TextInputLayout
//import com.kunaalkumar.suggsn.R
//import com.kunaalkumar.suggsn.RetrofitFactory
//import com.kunaalkumar.suggsn.tmdb.TMDbItem
//import com.kunaalkumar.suggsn.ui.MainActivity.Companion.BASE_BACKDROP_SIZE
//import com.kunaalkumar.suggsn.ui.MainActivity.Companion.BASE_IMAGE_URL
//import com.kunaalkumar.suggsn.ui.MainActivity.Companion.BASE_POSTER_SIZE
//import kotlinx.android.synthetic.main.fragment_search_loading.*
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//
//
//class SearchResults : Fragment() {
//
//    private val TAG: String = "SearchResults"
//    private val constraintSet = ConstraintSet()
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var viewAdapter: ResultsAdapter
//    private lateinit var viewManager: RecyclerView.LayoutManager
//
//    val service = RetrofitFactory.makeTMDbRetrofitService()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search_loading, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        search_button.setOnClickListener {
//
//            // Customize edit text to show as header
//            search_edit_text_layout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE)
//            search_edit_text_layout.isHintEnabled = false
//            search_edit_text.hint = null
//            background.visibility = View.GONE
//
//            // Start search transition
//            constraintSet.clone(context, R.layout.activity_main_results)
//            TransitionManager.beginDelayedTransition(fragment_search_results_layout)
//            constraintSet.applyTo(fragment_search_results_layout)
//            searchInProgress(true)
//
//
//        }
//    }
//
//    private fun initRecyclerView(dataSet: List<TMDbItem>) {
//        viewManager = LinearLayoutManager(context)
//        viewAdapter = ResultsAdapter(dataSet)
//        recyclerView = recycler_view.apply {
//            setHasFixedSize(true)
//            layoutManager = viewManager
//            adapter = viewAdapter
//        }
//        Log.d(TAG, "initRecyclerView: Successfully initialized recycler view and data set size ${dataSet.size}")
//    }
//
//    // Sets UI elements accordingly to bool argument
//    private fun searchInProgress(inProgress: Boolean) {
//        search_button.isEnabled = !inProgress
//        search_edit_text_layout.isEnabled = !inProgress
//    }
//}
