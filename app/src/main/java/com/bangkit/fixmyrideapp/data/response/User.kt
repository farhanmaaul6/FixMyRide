package com.bangkit.fixmyrideapp.data.response

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("email")
    val email: String
)
