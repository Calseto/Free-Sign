package com.freesign.firebase.firestore.collection

import android.util.Log
import com.freesign.firebase.firestore.Firestore.firestoreInstance
import com.freesign.model.Certificate
import com.freesign.utils.Authenticated
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.collections.ArrayList

object CertificateCollection {
    val certificateChannelRef = firestoreInstance.collection("certificates")
    
    var certificate = Certificate()
    var certificateDocumentRef = ""


    fun createCertificate(certificate: Certificate, onListen: (String) -> Unit) {
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

    fun getAllCertificate(onListen: (ArrayList<Certificate>) -> Unit): ListenerRegistration {
        return certificateChannelRef
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "CertificateMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = ArrayList<Certificate>()
                querySnapshot!!.documents!!.forEach {
                    var item = Certificate()
                    item = it.toObject(Certificate::class.java)!!
                    item.id = it.id

                    items.add(item)
                }

                onListen(items)
            }
    }
    
    fun getCertificateByDesigner(designerId: String, onListen: (Certificate) -> Unit): ListenerRegistration {
        return certificateChannelRef
            .whereEqualTo("designerId", designerId)
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "CertificateMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = Certificate()
                querySnapshot!!.documents!!.forEach {
                    items = it.toObject(Certificate::class.java)!!
                    items.id = it.id                     
                    certificateDocumentRef = it.id
                }

                onListen(items)
            }
    }
}