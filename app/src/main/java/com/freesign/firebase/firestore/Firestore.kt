package com.freesign.firebase.firestore

import android.util.Log
import com.freesign.model.User
import com.freesign.utils.Authenticated
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.collections.ArrayList

object Firestore {
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
}