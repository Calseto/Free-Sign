package com.freesign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.freesign.utils.Authenticated

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomePage : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(Authenticated.isValidCacheMember())
            findNavController().navigate(R.id.action_HomePage_to_HelloUser)

        view.findViewById<Button>(R.id.btnToEmployer).setOnClickListener {
            findNavController().navigate(R.id.action_HomePage_to_RegAndLogEmployee)
        }
    }
}