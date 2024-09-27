package com.bangkit.fixmyrideapp.data.response

import com.google.gson.annotations.SerializedName

data class NearbyRequest(
    @SerializedName("latitude")
    val latitude : String,

    @SerializedName("longitude")
    val longitude : String,

    @SerializedName("radius")
    val radius : String,

    @SerializedName("count")
    val count : String,
)
