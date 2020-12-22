package com.freesign

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.freesign.firebase.firestore.Firestore.firestoreInstance
import com.freesign.utils.Authenticated
import com.freesign.utils.Authenticated.context
import com.google.android.gms.tasks.Task
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    //    private User user;
    private val storage = FirebaseStorage.getInstance()
    private lateinit var byteArray: ByteArray
    private var picUri: Uri? = null
    private val downloadUri: Task<Uri>? = null
    private var character: HashMap<String, Any>? = null
    private val flag = 0
    private val name: String? = null
    private val yes = false
    private var uploadTask: UploadTask? = null
    private val ref: StorageReference? = null
    private var generatedFilePath: String? = null

    private var role: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestoreInstance.clearPersistence()

        Authenticated.init(this)

        var imageView = findViewById<ShapeableImageView>(R.id.character_image)

        val storage = FirebaseStorage.getInstance()

        var imageref = storage.reference.child("imageProfile/avatar.jpg")
        imageref.downloadUrl.addOnSuccessListener {Uri->
            val imageURL = Uri.toString()
            Glide.with(context)
                .load(imageURL)
                .into(imageView)
        }

        val pickerIntent = Intent(Intent.ACTION_PICK)
        pickerIntent.type = "image/*"
        startActivityForResult(pickerIntent, 100)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
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
                        this.contentResolver,
                        picUri)
                    val stream = ByteArrayOutputStream()
                    bp!!.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                    byteArray = stream.toByteArray()
                    (findViewById<View>(R.id.character_image) as ShapeableImageView).setImageBitmap(bp)
                    createCharacter()

                } catch (e: FileNotFoundException) {
                    throw RuntimeException(e)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
            //character.setImage(byteArray);
        }
    }

    private fun createCharacter() {

        val random = UUID.randomUUID().toString()
        val path = "characterImages/$random.png"
        val fireimageRef = storage.getReference(path)
        val metadata = StorageMetadata.Builder().setCustomMetadata("caption", (findViewById<View>(R.id.character_name) as EditText).text.toString()).build()
        uploadTask = fireimageRef.putBytes(byteArray, metadata)
        uploadTask!!.addOnProgressListener(this@MainActivity) { snapshot ->
            //(findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).visibility = View.VISIBLE
            //(findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).max = 10000
            //(findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).progress = (10000 * snapshot.bytesTransferred / snapshot.totalByteCount).toInt()
            //(findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).secondaryProgress = (10000 * snapshot.bytesTransferred / snapshot.totalByteCount).toInt() + 500
        }
        uploadTask!!.continueWithTask { task -> task.result!!.storage.downloadUrl }.addOnCompleteListener { task ->
            generatedFilePath = task.result.toString()
            /*character = HashMap()
            character!!["CharacterName"] = (findViewById<View>(R.id.character_name) as TextInputEditText).text.toString()
            character!!["Description"] = (findViewById<View>(R.id.description_character) as TextInputEditText).text.toString()
            character!!["ImageLink"] = generatedFilePath!!
            character!!["UID"]=sharedPreferences?.getString("UID",null).toString()
            characterViewModel!!.db.collection("characters")
                .add(character!!)
                .addOnSuccessListener { documentReference ->
                    finish()
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        */}.addOnFailureListener { e -> e.message?.let { Log.d("Failyre", it) } }
    }

    override fun onDestroy() {
        super.onDestroy()
        //        realm1.close(); // Remember to close Realm when done.
    }

    companion object {
        private const val TAG = "Log"
    }
}