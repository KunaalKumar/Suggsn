package com.kunaalkumar.sugsn

import androidx.lifecycle.ViewModel
import com.kunaalkumar.sugsn.movies.MoviesFragment

class MainActivityViewModel : ViewModel() {

    val moviesFragment: MoviesFragment = MoviesFragment()

}