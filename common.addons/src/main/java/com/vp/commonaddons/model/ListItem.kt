package com.vp.commonaddons.model

import com.google.gson.annotations.SerializedName

data class ListItem(@SerializedName("Title") var title: String? = null,
                    @SerializedName("Year") var year: String? = null,
                    @SerializedName("imdbID") var imdbID: String? = null,
                    @SerializedName("Poster") var poster: String? = null)