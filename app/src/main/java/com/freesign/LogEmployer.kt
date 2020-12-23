package com.freesign

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.freesign.firebase.firestore.collection.UserCollection
import com.freesign.firebase.firestore.collection.UserCollection.login
import com.freesign.firebase.messaging.FirebaseCloudMessaging
import com.freesign.firebase.messaging.FirebaseCloudMessagingService
import com.freesign.model.User
import com.freesign.utils.Authenticated
import com.freesign.utils.BaseFragment

class LogEmployer : BaseFragment() {

    var dialog: SweetAlertDialog? = null
    var user: User = User()

    lateinit var edtTxtEmail: EditText
    lateinit var edtTxtPassword: EditText
    lateinit var btnLogin: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_employer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtTxtEmail = view.findViewById(R.id.edtTxtEmail)
        edtTxtPassword = view.findViewById(R.id.edtTxtPassword)
        btnLogin = view.findViewById(R.id.btnLogin)

        view.findViewById<TextView>(R.id.btnLogin).setOnClickListener {
            dialog = showProgress()

            val email = edtTxtEmail.text.toString().trimEnd()
            val password = edtTxtPassword.text.toString()

            UserCollection.login(email, password, Authenticated.getRole()!!, this::handleLogin)
        }
    }

    fun handleLogin(user: User) {
        dialog?.dismiss()

        if(user.firstname!=null) {
            dialog = showSuccess("Berhasil login")
            FirebaseCloudMessaging.getToken(user)
            dialog!!.setConfirmClickListener {
                dialog!!.dismiss()
                view?.findNavController()?.navigate(R.id.action_LogEmployer_to_HelloUser)
            }
        } else {
            dialog = showError("User tidak ditemukan")
        }
    }
}