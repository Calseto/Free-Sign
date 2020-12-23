package com.freesign.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Post (
    var id: String? = null,
    var designerId: String? = null,
    var rating: Float? = null,
    var status: String? = null,
    var createdAt: Any? = null
)