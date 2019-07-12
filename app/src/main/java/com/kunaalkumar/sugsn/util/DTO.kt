package com.kunaalkumar.sugsn.util

data class ListItem(
    val title: String,
    val poster: String,
    val rating: String,
    val link: String
) {
    fun getId(): String {
        return "tt\\w+".toRegex().find(link)!!.groups[0]!!.value
    }
}


data class OMDB_Result(
    val Title: String,
    val Ratings: List<OMDB_RATING>,
    val tomatoURL: String
)

data class OMDB_RATING(
    val Source: String,
    val Value: String
)