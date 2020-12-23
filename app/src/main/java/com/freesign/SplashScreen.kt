package com.freesign

import android.content.Intent
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            if(Authenticated.isValidCacheMember())
                findNavController().navigate(R.id.action_SplashScreen_to_HelloUser)
            else
                findNavController().navigate(R.id.action_SplashScreen_to_HomePage)
        }
    }
}