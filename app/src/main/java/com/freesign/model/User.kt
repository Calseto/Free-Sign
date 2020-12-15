package com.freesign.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var id: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var password: String? = null,
    var email: String? = null,
    var role: String? = null,
    var FCMToken: String? = null
) : Parcelable