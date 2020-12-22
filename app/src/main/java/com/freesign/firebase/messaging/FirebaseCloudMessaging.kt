package com.freesign.firebase.messaging

import android.util.Log
import com.freesign.firebase.firestore.collection.UserCollection
import com.freesign.model.User
import com.google.firebase.iid.FirebaseInstanceId

object FirebaseCloudMessaging {
    fun getToken(user: User) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener {
                user.FCMToken = it.token
                UserCollection.updateUser(user, this::tokenUpdateCallback)
        }.addOnFailureListener {
                it.message?.let { it1 -> Log.d("fcm", it1) }
        }
    }

    fun tokenUpdateCallback(result: String) {

    }
}
