package com.freesign.firebase.firestore.collection

import android.util.Log
import com.freesign.firebase.firestore.Firestore.firestoreInstance
import com.freesign.model.Post
import com.freesign.utils.Authenticated
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.collections.ArrayList

object PostCollection {
    val certificateChannelRef = firestoreInstance.collection("certificates")
    
    var certificate = Post()
    var certificateDocumentRef = ""


    fun createPost(certificate: Post, onListen: (String) -> Unit) {
        certificateChannelRef
            .add(certificate)
            .addOnSuccessListener {
                onListen(it.id)
            }
            .addOnFailureListener {
                Log.d("error", it.toString())
                onListen("error")
            }
    }

    fun getAllPost(onListen: (ArrayList<Post>) -> Unit): ListenerRegistration {
        return certificateChannelRef
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "PostMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = ArrayList<Post>()
                querySnapshot!!.documents!!.forEach {
                    var item = Post()
                    item = it.toObject(Post::class.java)!!
                    item.id = it.id

                    items.add(item)
                }

                onListen(items)
            }
    }
    
    fun getPostByDesigner(designerId: String, onListen: (Post) -> Unit): ListenerRegistration {
        return certificateChannelRef
            .whereEqualTo("designerId", designerId)
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "PostMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = Post()
                querySnapshot!!.documents!!.forEach {
                    items = it.toObject(Post::class.java)!!
                    items.id = it.id                     
                    certificateDocumentRef = it.id
                }

                onListen(items)
            }
    }
}