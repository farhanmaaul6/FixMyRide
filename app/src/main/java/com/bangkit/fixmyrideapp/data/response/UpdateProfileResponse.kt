package com.bangkit.fixmyrideapp.data.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse (
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("link")
    val link: String,
)