package com.vp.commonaddons.model

import com.google.gson.annotations.SerializedName

data class ListItem(@SerializedName("Title") val title: String,
                    @SerializedName("Year") val year: String,
                    @SerializedName("imdbID") val imdbID: String,
                    @SerializedName("Poster") val poster: String)