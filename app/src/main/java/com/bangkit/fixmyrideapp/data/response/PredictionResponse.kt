package com.bangkit.fixmyrideapp.data.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @field:SerializedName("Confidence")
    val Confidence: String,

    @field:SerializedName("Predicted Class")
    val Predicted_Class: String,

    @field:SerializedName("Solusi")
    val Solusi: String
)
