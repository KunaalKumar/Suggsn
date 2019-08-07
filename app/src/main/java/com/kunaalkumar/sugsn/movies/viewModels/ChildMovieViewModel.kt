package com.kunaalkumar.sugsn.movies.viewModels

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kunaalkumar.sugsn.RecyclerViewAdapter
import com.kunaalkumar.sugsn.imdb.ImdbRepository
import com.kunaalkumar.sugsn.movies.MOVIE_TYPE
import com.kunaalkumar.sugsn.omdb.OMDBRepository
import com.kunaalkumar.sugsn.util.ListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


// Needs to be set with FactoryViewModel
class ChildMovieViewModel(val type: MOVIE_TYPE) : ViewModel() {

    lateinit var adapter: RecyclerViewAdapter
    var listState: Parcelable? = null
    var isAdapterInitialized = false

    private lateinit var itemQueue: Queue<ListItem>

    init {
        CoroutineScope(IO).launch {
            getMostPopularMovies()
        }
    }

    private suspend fun getMostPopularMovies() {
        itemQueue = when (type) {
            MOVIE_TYPE.TOP -> LinkedList(ImdbRepository.getTopRatedMovies())
            MOVIE_TYPE.POPULAR -> LinkedList(ImdbRepository.getPopularMovies())
        }
        withContext(Main) {
            loadNextTenItems()
        }
    }

    // Removes the first ten elements from the queue and adds them to the adapter
    fun loadNextTenItems() {
        for (i in 1..10) { // Pop first ten items from queue
            if (::itemQueue.isInitialized && itemQueue.size != 0) {
                val item = itemQueue.remove()
                CoroutineScope(IO).launch {
                    item.rottenRating = OMDBRepository.getRottenRating(item.getId())
                    withContext(Main) {
                        adapter.updateItem(item)
                    }
                }
                adapter.addItem(item)
            }
        }
    }
}

class FactoryViewModel(private val type: MOVIE_TYPE) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChildMovieViewModel(type) as T
    }
}