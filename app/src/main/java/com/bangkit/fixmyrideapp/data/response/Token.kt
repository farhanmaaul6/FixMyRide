package com.bangkit.fixmyrideapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    var token: String? = null
) : Parcelable
