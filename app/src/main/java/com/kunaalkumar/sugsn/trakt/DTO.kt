package com.kunaalkumar.sugsn.trakt

data class TraktTrendingMovies(
    val watchers: Int, // 18
    val movie: Movie
)

data class Movie(
    val title: String, // Spider-Man: Into the Spider-Verse
    val year: Int, // 2018
    val ids: Ids
) {
    data class Ids(
        val trakt: Int, // 205404
        val slug: String, // spider-man-into-the-spider-verse-2018
        val imdb: String, // tt4633694
        val tmdb: Int // 324857
    )
}
