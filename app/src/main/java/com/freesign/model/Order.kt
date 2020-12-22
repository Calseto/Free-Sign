package com.freesign.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Order (
    var id: String? = null,
    var employerId: String? = null,
    var designerId: String? = null,
    var title: String? = null,
    var description: String? = null,
    var fee: Int? = null,
    var rating: Float? = null,
    var status: String? = null,
    var closedAt: Any? = null,
    var createdAt: Any? = null,
)