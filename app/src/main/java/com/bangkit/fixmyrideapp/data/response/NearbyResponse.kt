package com.bangkit.fixmyrideapp.data.response

import com.google.gson.annotations.SerializedName

data class NearbyResponse(
    @SerializedName("status")
    val status : String,

    @SerializedName("message")
    val message : String,

    @SerializedName("result")
    val result : List<NearbyItem>,
)

data class NearbyItem(
    @SerializedName("id")
    val id : String,

    @SerializedName("nationalPhoneNumber")
    val nationalPhoneNumber : String,

    @SerializedName("name")
    val name : String,

    @SerializedName("rating")
    val rating : Float,

    @SerializedName("formattedAddress")
    val formattedAddress : String,

    @SerializedName("latitude")
    val latitude : Double,

    @SerializedName("longitude")
    val longitude : Double,

)
