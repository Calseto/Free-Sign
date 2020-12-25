package com.freesign.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freesign.R
import com.freesign.firebase.storage.Storage
import com.freesign.model.User
import kotlinx.android.synthetic.main.row_search_designer.view.*
import kotlin.collections.ArrayList

class DesignerAdapter(private val Users:ArrayList<User>, private val context: Context) : RecyclerView.Adapter<DesignerAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_search_designer, parent, false), context)
    }

    override fun getItemCount(): Int = Users.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindKabarCluster(Users[position])
    }

    inner class UserViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        val view = view

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindKabarCluster(user: User) {
            itemView.apply {
                name.text = user.firstname + " " + user.lastname
                var imageref = Storage.storageInstance.reference.child("imageProfile/${user.image}")
                imageref.downloadUrl.addOnSuccessListener {Uri->
                    val imageURL = Uri.toString()
                    Glide.with(view)
                        .load(imageURL)
                        .circleCrop()
                        .into(image)
                }
                type.text = user.specialization
            }
        }
    }
}