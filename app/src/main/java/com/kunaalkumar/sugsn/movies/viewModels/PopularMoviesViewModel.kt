package com.kunaalkumar.sugsn.movies.viewModels

import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.RecyclerViewAdapter
import com.kunaalkumar.sugsn.imdb.ImdbRepository
import com.kunaalkumar.sugsn.omdb.OMDBRepository
import com.kunaalkumar.sugsn.util.ListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class PopularMoviesViewModel : ViewModel(), VMWrapper {
    override val adapter: RecyclerViewAdapter = RecyclerViewAdapter()

    private lateinit var itemQueue: Queue<ListItem>

    init {
        CoroutineScope(IO).launch {
            getMostPopularMovies()
        }
    }

    private suspend fun getMostPopularMovies() {
        itemQueue = LinkedList(ImdbRepository.getPopularMovies())
        withContext(Main) {
            loadNextTenItems()
        }
    }

    // Removes the first ten elements from the queue and adds them to the adapter
    override fun loadNextTenItems() {
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