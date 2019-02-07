package com.kunaalkumar.suggsn.tmdb

val TV_MEDIA_TYPE: String = "tv"
val MOVIE_MEDIA_TYPE: String = "movie"
val PERSON_MEDIA_TYPE: String = "person"

data class TMDbCallback(val page: Int, val total_results: Int, val total_pages: Int, val results: List<TMDbItem>)

data class TMDbItem(
    val poster_path: String, val adult: Boolean, val overview: String, val release_data: String,
    val id: Int, val media_type: String, val title: String, val backdrop_path: String,
    val popularity: Double, val vote_average: Double, val genre_ids: List<Int>,
    val first_air_date: String, val profile_path: String, val name: String, val known_for: List<TMDbItem>
)
