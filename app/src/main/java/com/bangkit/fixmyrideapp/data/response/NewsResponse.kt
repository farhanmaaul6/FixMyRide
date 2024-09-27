package com.bangkit.fixmyrideapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsResponse (

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<NewsData>
)

data class NewsData(

    @SerializedName("id")
    val id: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("link_url")
    val link_url: String,

    @SerializedName("photo_url")
    val photo_url: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("type")
    val type: String,
)