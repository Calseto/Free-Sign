package com.freesign.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Certificate (
    var id: String? = null,
    var designerId: String? = null,
    var title: String? = null,
    var description: String? = null,
    var createdAt: Any? = null,
)