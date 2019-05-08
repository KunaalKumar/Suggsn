package com.kunaalkumar.sugsn.tmdb

import com.kunaalkumar.sugsn.repositories.TmdbRepository
import java.io.Serializable

const val TV_MEDIA_TYPE: String = "tv"
const val MOVIE_MEDIA_TYPE: String = "movie"
const val PERSON_MEDIA_TYPE: String = "person"

data class TMDbCallback<T>(val page: Int, val total_results: Int, val total_pages: Int, val results: List<T>)

interface TMDbItem {
    val poster_path: String
    val popularity: Double
    val id: Int
    val backdrop_path: String
    val vote_average: Double
    val overview: String
    val genre_ids: List<Int>
    val original_language: String
    val vote_count: Int

    fun getPoster(): String? {
        return TmdbRepository.BASE_IMAGE_URL + TmdbRepository.BASE_POSTER_SIZE + poster_path
    }

    fun getBackdrop(): String? {
        return TmdbRepository.BASE_IMAGE_URL + TmdbRepository.BASE_BACKDROP_SIZE + backdrop_path
    }
}

interface TMDbMovieInfo : TMDbItem {
    override val poster_path: String
    override val popularity: Double
    override val id: Int
    override val backdrop_path: String
    override val vote_average: Double
    override val overview: String
    override val genre_ids: List<Int>
    override val original_language: String
    override val vote_count: Int
    val adult: Boolean
    val release_data: String
    val original_title: String
    val title: String
    val video: Boolean
}

data class TMDbMovieItem(
    override val poster_path: String,
    override val popularity: Double,
    override val id: Int,
    override val backdrop_path: String,
    override val vote_average: Double,
    override val overview: String,
    override val genre_ids: List<Int>,
    override val original_language: String,
    override val vote_count: Int,
    override val adult: Boolean,
    override val release_data: String,
    override val original_title: String,
    override val title: String,
    override val video: Boolean
) : TMDbMovieInfo

data class TMDbMovieDetails(
    override val poster_path: String,
    override val popularity: Double,
    override val id: Int,
    override val backdrop_path: String,
    override val vote_average: Double,
    override val overview: String,
    override val genre_ids: List<Int>,
    override val original_language: String,
    override val vote_count: Int,
    override val adult: Boolean,
    override val release_data: String,
    override val original_title: String,
    override val title: String,
    override val video: Boolean,
    val runtime: String,
    val homepage: String,
    val imdb_id: String,
    val tagline: String,
    val status: String
) : TMDbMovieInfo, Serializable

data class TMDbConfigCallback(val images: TMDbImageConfig)

data class TMDbImageConfig(val base_url: String, val backdrop_sizes: List<String>, val poster_sizes: List<String>)

data class TMDbGenre(val id: Int, val name: String)

data class TMDbVideos(val id: Int, val results: List<TMDbVideo>)

data class TMDbVideo(val id: String, val name: String, val key: String, val type: String)