package com.kunaalkumar.sugsn.movies.viewModels

import com.kunaalkumar.sugsn.RecyclerViewAdapter

interface VMWrapper {
    val adapter: RecyclerViewAdapter
    fun loadNextTenItems()
}