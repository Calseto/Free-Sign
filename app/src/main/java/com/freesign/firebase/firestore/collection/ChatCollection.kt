package com.freesign.firebase.firestore.collection

import android.util.Log
import com.freesign.firebase.firestore.Firestore.firestoreInstance
import com.freesign.model.Chat
import com.freesign.utils.Authenticated
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.collections.ArrayList

object ChatCollection {
    val chatChannelRef = firestoreInstance.collection("chats")
    var chatDocumentRef = ""

    fun createChat(chat: Chat, onListen: (String) -> Unit) {
        chatChannelRef
            .add(chat)
            .addOnSuccessListener {
                chatDocumentRef = it.id
                onListen(it.id)
            }
            .addOnFailureListener {
                Log.d("error", it.toString())
                onListen("error")
            }
    }

    fun getAllChat(from: String, to: String, onListen: (ArrayList<Chat>) -> Unit): ListenerRegistration {
        return chatChannelRef
            .whereEqualTo("from", to)
            .whereEqualTo("to", to)
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = ArrayList<Chat>()
                querySnapshot!!.documents!!.forEach {
                    var item = Chat()
                    item = it.toObject(Chat::class.java)!!
                    item.id = it.id
                    items.add(item)
                }

                onListen(items)
            }
    }
}