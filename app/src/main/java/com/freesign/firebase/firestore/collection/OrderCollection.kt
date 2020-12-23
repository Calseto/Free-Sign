package com.freesign.firebase.firestore.collection

import android.util.Log
import com.freesign.firebase.firestore.Firestore.firestoreInstance
import com.freesign.model.Order
import com.freesign.utils.Authenticated
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.collections.ArrayList

object OrderCollection {
    val orderChannelRef = firestoreInstance.collection("orders")
    
    var order = Order()
    var orderDocumentRef = ""


    fun createOrder(order: Order, onListen: (String) -> Unit) {
        orderChannelRef
            .add(order)
            .addOnSuccessListener {
                onListen(it.id)
            }
            .addOnFailureListener {
                Log.d("error", it.toString())
                onListen("error")
            }
    }

    fun getAllOrder(onListen: (ArrayList<Order>) -> Unit): ListenerRegistration {
        return orderChannelRef
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "OrderMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = ArrayList<Order>()
                querySnapshot!!.documents!!.forEach {
                    var item = Order()
                    item = it.toObject(Order::class.java)!!
                    item.id = it.id

                    items.add(item)
                }

                onListen(items)
            }
    }
    
    fun getOrderByDesigner(designerId: String, onListen: (Order) -> Unit): ListenerRegistration {
        return orderChannelRef
            .whereEqualTo("designerId", designerId)
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "OrderMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = Order()
                querySnapshot!!.documents!!.forEach {
                    items = it.toObject(Order::class.java)!!
                    items.id = it.id                     
                    orderDocumentRef = it.id
                }

                onListen(items)
            }
    }


    fun getOrderByEmployer(employerId: String, onListen: (Order) -> Unit): ListenerRegistration {
        return orderChannelRef
            .whereEqualTo("employerId", employerId)
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "OrderMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                var items = Order()
                querySnapshot!!.documents!!.forEach {
                    items = it.toObject(Order::class.java)!!
                    items.id = it.id                  
                    orderDocumentRef = it.id
                }

                onListen(items)
            }
    }
    
    fun updateOrder(order: Order, onListen: (String) -> Unit)  {
        orderChannelRef
            .document(orderDocumentRef)
            .update(
                "status", order.status,
                "rating", order.rating,
            )
            .addOnSuccessListener {
                this.order = order
                onListen("success")
            }
            .addOnFailureListener {
                onListen(it.message!!)
            }
    }
}