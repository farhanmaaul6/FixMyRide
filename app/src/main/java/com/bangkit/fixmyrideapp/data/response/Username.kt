package com.bangkit.fixmyrideapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Username(
    var name: String? = null
) : Parcelable
