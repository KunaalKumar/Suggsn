package com.kunaalkumar.suggsn.tmdb

import com.kunaalkumar.suggsn.repositories.TmdbRepository

val TV_MEDIA_TYPE: String = "tv"
val MOVIE_MEDIA_TYPE: String = "movie"
val PERSON_MEDIA_TYPE: String = "person"

data class TMDbCallback(val page: Int, val total_results: Int, val total_pages: Int, val results: List<TMDbItem>)

data class TMDbItem(
    private val poster_path: String?, val adult: Boolean, val overview: String, val release_data: String,
    val id: Int, val media_type: String, val title: String?, val backdrop_path: String?,
    val popularity: Double, val vote_average: Double, val genre_ids: List<Int>,
    val first_air_date: String, val profile_path: String, val name: String?, val known_for: List<TMDbItem>?
) {
    fun getPoster(): String? {
        if (poster_path == null) {
            return null
        }
        return TmdbRepository.BASE_IMAGE_URL + TmdbRepository.BASE_POSTER_SIZE + poster_path
    }

    fun getBackdrop(): String? {
        if (backdrop_path == null) {
            return null
        }
        return TmdbRepository.BASE_IMAGE_URL + TmdbRepository.BASE_BACKDROP_SIZE + backdrop_path
    }
}

data class TMDbConfigCallback(val images: TMDbImageConfig)

data class TMDbImageConfig(val base_url: String, val backdrop_sizes: List<String>, val poster_sizes: List<String>)
