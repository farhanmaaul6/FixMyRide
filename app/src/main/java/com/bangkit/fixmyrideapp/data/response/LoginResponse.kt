package com.bangkit.fixmyrideapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: User
)