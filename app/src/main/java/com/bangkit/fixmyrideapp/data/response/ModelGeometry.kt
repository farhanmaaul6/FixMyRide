package com.bangkit.fixmyrideapp.data.response

import com.google.gson.annotations.SerializedName

class ModelGeometry {
    @SerializedName("location")
    lateinit var modelLocation: ModelLocation
}