package com.kunaalkumar.sugsn.view_model

import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.RecyclerViewAdapter
import com.kunaalkumar.sugsn.imdb.ImdbRepository
import com.kunaalkumar.sugsn.omdb.OMDBRepository
import com.kunaalkumar.sugsn.util.ListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MainActivityViewModel : ViewModel() {

    val TAG: String = "Sugsn@MainActivityViewModel"
    val adapter: RecyclerViewAdapter = RecyclerViewAdapter()

    private lateinit var itemQueue: Queue<ListItem>
    private val mDisposable = CompositeDisposable()
    private val topRatedMoviesList by lazy {
        ImdbRepository.getTopRatedMovies()
    }

    fun getTopMovies() {
        mDisposable.add(topRatedMoviesList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movieList ->
                itemQueue = LinkedList(movieList)
                loadNextTenItems()
            }
        )
    }

    // Removes the first ten elements from the queue and adds them to the adapter
    fun loadNextTenItems() {
        for (i in 1..10) { // Pop first ten items from queue
            val item = itemQueue.remove()
            mDisposable.add(
                OMDBRepository.getRottenRating(item.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response ->
                        item.rottenRating = response
                        adapter.updateItem(item)
                    }
            )
            adapter.addItem(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}

