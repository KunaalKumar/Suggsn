package com.kunaalkumar.sugsn.tmdb

import com.kunaalkumar.sugsn.repositories.TmdbRepository

const val TV_MEDIA_TYPE: String = "tv"
const val MOVIE_MEDIA_TYPE: String = "movie"
const val PERSON_MEDIA_TYPE: String = "person"

data class TMDbCallback<T>(val page: Int, val total_results: Int, val total_pages: Int, val results: List<T>)

data class TMDbItem(
    private val poster_path: String?, val adult: Boolean, val overview: String, val release_data: String,
    val id: Int, val media_type: String, val title: String?, private val backdrop_path: String?,
    val popularity: Double, val vote_average: Double, val genre_ids: List<Int>,
    val first_air_date: String, private val profile_path: String?, val name: String?, val known_for: List<TMDbItem>?
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

    fun getProfilePath(): String? {
        if (profile_path == null) {
            return null
        }
        return TmdbRepository.BASE_IMAGE_URL + TmdbRepository.BASE_POSTER_SIZE + profile_path
    }
}

data class TMDbMovieItem(
    val adult: Boolean, private val backdrop_path: String?, val budget: Int, val genres: List<TMDbGenre>,
    val homepage: String, val id: Int, val original_title: String, val overview: String,
    val popularity: Double, private val poster_path: String?, val release_date: String, val revenue: Int,
    val runtime: Int, val status: String, val vote_average: Double, val vote_count: Int, val tagline: String
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

data class TMDbGenre(val id: Int, val name: String)

data class TMDbVideos(val id: Int, val results: List<TMDbVideo>)

data class TMDbVideo(val id: String, val name: String, val key: String, val type: String)