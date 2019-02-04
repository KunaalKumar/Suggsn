package com.kunaalkumar.suggsn.taste_dive

data class TDResult(val Similar: TDSimilar)
data class TDSimilar(val Info: List<TDItem>, val Result: List<TDItem>)
data class TDItem(val Name: String, val Type: String)

