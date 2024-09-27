package com.bangkit.fixmyrideapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Email(
    var email: String? = null
):Parcelable
