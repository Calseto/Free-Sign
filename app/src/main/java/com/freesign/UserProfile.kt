package com.freesign

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.freesign.firebase.storage.Storage
import com.freesign.firebase.storage.Storage.storageInstance
import com.freesign.model.User
import com.freesign.utils.Authenticated
import kotlinx.android.synthetic.main.user_profile.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

/*

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
    }*/

 */
class UserProfile : Fragment() {

    var user = User()

    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var password: TextView
    lateinit var location: TextView
    lateinit var image: ImageView

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
    }
}