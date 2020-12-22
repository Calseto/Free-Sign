package com.freesign

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.freesign.firebase.firestore.collection.UserCollection
import com.freesign.model.User
import com.freesign.utils.Authenticated
import com.freesign.utils.BaseFragment
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.register_employer.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegEmployer : BaseFragment() {

    var dialog: SweetAlertDialog? = null
    var user: User = User()

    lateinit var register: ListenerRegistration
    lateinit var getUserByUsername: ListenerRegistration
    lateinit var getUserByEmail: ListenerRegistration

    lateinit var edtTxtFirstName: EditText
    lateinit var edtTxtLastName: EditText
    lateinit var edtTxtLocation: EditText
    lateinit var edtTxtPhoneNumber: EditText
    lateinit var edtTxtEmail: EditText
    lateinit var edtTxtPassword: EditText
    lateinit var btnRegister: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.register_employer, container, false)

        edtTxtFirstName = view.findViewById(R.id.edtTxtFirstName)
        edtTxtLastName = view.findViewById(R.id.edtTxtLastName)
        edtTxtLocation = view.findViewById(R.id.edtTxtLocation)
        edtTxtPhoneNumber = view.findViewById(R.id.edtTxtPhoneNumber)
        edtTxtEmail = view.findViewById(R.id.edtTxtEmail)
        edtTxtPassword = view.findViewById(R.id.edtTxtPassword)
        btnRegister = view.findViewById(R.id.btnRegister)

        view.findViewById<Button>(R.id.btnRegister).setOnClickListener {
            dialog = showProgress()

            user.firstname = edtTxtFirstName.text.toString().trimEnd()
            user.lastname = edtTxtLastName.text.toString().trimEnd()
            user.location = edtTxtLocation.text.toString().trimEnd()
            user.phoneNumber = edtTxtPhoneNumber.text.toString().trimEnd()
            user.email = edtTxtEmail.text.toString().trimEnd()
            user.password = edtTxtPassword.text.toString().trimEnd()
            user.role = Authenticated.getRole()

            validate()
        }
        view.findViewById<Button>(R.id.employer_registertologin_button).setOnClickListener {
            findNavController().navigate(R.id.action_RegAndLogEmployer_to_LogEmployer)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val h2 = Handler()
        val r2: Runnable = object : Runnable {
            override fun run() {
                if(edtTxtFirstName.text!!.isNotEmpty()
                    && edtTxtLastName.text!!.isNotEmpty()
                    && edtTxtLocation.text!!.isNotEmpty()
                    && edtTxtPhoneNumber.text!!.isNotEmpty()
                    && edtTxtEmail.text!!.isNotEmpty()
                    && edtTxtPassword.text!!.isNotEmpty()) {
                    btnRegister.isEnabled = true
                    btnRegister.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                } else {
                    btnRegister.isEnabled = false
                    btnRegister.setBackgroundColor(Color.parseColor("#BBBBBB"))
                }
                h2.postDelayed(this, 500)
            }
        }
        r2.run()
    }

    fun validate() {
        if(!user.email.toString().contains("@")) {
            dialog!!.dismiss()
            dialog = showError("Format email tidak sesuai")
            return
        } else if(user.password!!.length < 8) {
            dialog!!.dismiss()
            dialog = showError("Panjang password kurang dari 8 karakter")
            return
        }

        getUserByUsername = UserCollection.getUserByName(user.firstname!!, user.lastname!!, "employer", this::checkName)
        return
    }

    fun checkName(userWithSameName: User) {
        getUserByUsername.remove()

        if(userWithSameName.firstname != null) {
            dialog!!.dismiss()
            dialog = showError("Nama tersebut telah digunakan")
        } else {
            getUserByEmail = UserCollection.getUserByEmail(user.email!!, "employer", this::checkEmail)
        }

        return
    }

    fun checkEmail(userWithSameEmail: User) {
        getUserByEmail.remove()

        if (userWithSameEmail.firstname != null) {
            dialog!!.dismiss()
            dialog = showError("Email telah digunakan")
        } else {
            user.image = "avatar.jpg"
            UserCollection.register(user, this::successRegister)
        }

        return
    }

    fun successRegister(result: String) {
        dialog!!.dismiss()
        dialog = showSuccess("Berhasil melakukan pendaftaran")
        dialog!!.setConfirmClickListener {
            dialog!!.dismiss()
            view?.findNavController()?.navigate(R.id.action_RegEmployer_to_LogEmployer)
        }
        return
    }
}