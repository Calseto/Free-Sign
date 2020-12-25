package com.freesign

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.freesign.firebase.firestore.collection.UserCollection
import com.freesign.firebase.storage.Storage
import com.freesign.firebase.storage.Storage.storageInstance
import com.freesign.model.User
import com.freesign.utils.Authenticated
import com.freesign.utils.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.main_menu.*
import kotlinx.android.synthetic.main.user_profile.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class MainMenu : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_nav.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener)

    }

    private val OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId) {
            R.id.miLocation -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.miLangganan -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.miSearchDesigner -> {
                val fragment = SearchDesigner()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frame, fragment, fragment.javaClass.getSimpleName())
                    ?.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.miAccount -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}