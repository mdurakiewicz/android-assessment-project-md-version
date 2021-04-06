package com.vp.detail.model

import com.google.gson.annotations.SerializedName
import com.vp.commonaddons.model.ListItem

data class MovieDetail(@SerializedName("imdbID") val imdbID: String,
                       @SerializedName("Title") val title: String,
                       @SerializedName("Year") val year: String,
                       @SerializedName("Runtime") val runtime: String,
                       @SerializedName("Director") val director: String,
                       @SerializedName("Plot") val plot: String,
                       @SerializedName("Poster") val poster: String)

fun MovieDetail?.toListItem(): ListItem? {
    this ?: return null
    return ListItem(
            title = title,
            year = year,
            imdbID = imdbID,
            poster = poster
    )
}