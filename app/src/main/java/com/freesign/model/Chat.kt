package com.freesign.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Chat (
    var id: String? = null,
    var from: String? = null,
    var to: String? = null,
    var type: String? = null,
    var content: String? = null,
    var readAt: Any? = null,
    var createdAt: Any? = null,
)