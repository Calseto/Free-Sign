package com.freesign.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class User (
    var id: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var password: String? = null,
    var email: String? = null,
    var role: String? = null,
    var specialization: String? = null,
    var image: String? = null,
    var rating: Int? = null,
    var cv: Int? = null,
    var category: String? = null,
    var phoneNumber: String? = null,
    var location: String? = null,
    var salaryMin: Int? = null,
    var salaryMax: Int? = null,
    var FCMToken: String? = null
)