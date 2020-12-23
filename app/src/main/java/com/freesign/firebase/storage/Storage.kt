package com.freesign.firebase.storage

import android.util.Log
import com.freesign.model.User
import com.freesign.utils.Authenticated
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import kotlin.collections.ArrayList

object Storage {
    val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
}