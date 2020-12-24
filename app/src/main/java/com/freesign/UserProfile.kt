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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.user_profile.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class UserProfile : BaseFragment() {

    var user = User()

    private val storage = FirebaseStorage.getInstance()
    private lateinit var byteArray: ByteArray
    private var picUri: Uri? = null
    private var uploadTask: UploadTask? = null
    private var generatedFilePath: String? = null

    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var password: TextView
    lateinit var location: TextView
    lateinit var image: ImageView

    var dialog: SweetAlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = Authenticated.getUser()

        image = view.findViewById(R.id.image_profile)
        name = view.findViewById(R.id.user_profile_name)
        email = view.findViewById(R.id.user_profile_email)
        password = view.findViewById(R.id.user_profile_pass)
        location = view.findViewById(R.id.user_profile_location)

        var imageref = storageInstance.reference.child("imageProfile/${user.image}")
        imageref.downloadUrl.addOnSuccessListener {Uri->
            val imageURL = Uri.toString()
            Glide.with(view)
                .load(imageURL)
                .circleCrop()
                .into(image)
        }

        name.text = user.firstname + " " + user.lastname
        email.text = user.email
        password.text = user.password
        location.text = user.location

        view.findViewById<TextView>(R.id.profile_user_nextbutton).setOnClickListener {
            
        }

        view.findViewById<TextView>(R.id.profile_user_logoutbutton).setOnClickListener{
            findNavController().navigate(R.id.action_UserProfile_to_HomePage)
            Authenticated.destroySession()
        }

        image_change.setOnClickListener {
            val pickerIntent = Intent(Intent.ACTION_PICK)
            pickerIntent.type = "image/*"
            startActivityForResult(pickerIntent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && intent?.getData()!=null) {
            intent!!.data!!.path?.let { Log.d("path", it) }
            picUri = intent.data
            var bp: Bitmap? = null
            if (picUri != null) {
                try {
                    bp = MediaStore.Images.Media.getBitmap(
                        activity?.contentResolver,
                        picUri)
                    val stream = ByteArrayOutputStream()
                    bp!!.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                    byteArray = stream.toByteArray()
                    view?.let {
                        Glide.with(it)
                            .load(bp)
                            .circleCrop()
                            .into(image)
                    }
                    uploadImage()
                } catch (e: FileNotFoundException) {
                    throw RuntimeException(e)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }
    }

    private fun uploadImage() {
        val random = UUID.randomUUID().toString()
        val path = "imageProfile/$random.png"
        val fireimageRef = storage.getReference(path)
        val metadata = StorageMetadata.Builder().setCustomMetadata("caption", user.firstname + " " + user.lastname).build()

        dialog = showProgress()

        uploadTask = fireimageRef.putBytes(byteArray, metadata)

        uploadTask!!.continueWithTask { task -> task.result!!.storage.downloadUrl }.addOnCompleteListener { task ->
            generatedFilePath = task.result.toString()
            
            var user = Authenticated.getUser()

            user.image = "$random.png"

            UserCollection.updateUser(user, this::successUploadImage)
        }.addOnFailureListener { e -> e.message?.let {
            dialog?.dismiss()
            dialog = showError("Gagal mengganti profil")
        } }
    }

    private fun successUploadImage(result: String) {
        dialog?.dismiss()
        dialog = showSuccess("Berhasil mengganti profil")
    }
}