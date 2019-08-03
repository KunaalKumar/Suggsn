package com.kunaalkumar.sugsn.util

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ListItem(
    val title: String,
    val year: String,
    val poster: String,
    val imdbRating: String?,
    var rottenRating: String?,
    val link: String
) {
    fun getId(): String {
        return "tt\\w+".toRegex().find(link)!!.groups[0]!!.value
    }
}

@JsonClass(generateAdapter = true)
data class OmdbObject(
    @Json(name = "Title") val title: String,
    @Json(name = "Ratings") val ratings: List<OmdbRating>,
    val tomatoURL: String
)

@JsonClass(generateAdapter = true)
data class OmdbRating(
    @Json(name = "Source") val source: String,
    @Json(name = "Value") val value: String
)