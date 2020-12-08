package com.freesign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegAndLogEmployer : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_and_login_employer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.regAndLog_employer_first_button).setOnClickListener {
            findNavController().navigate(R.id.action_RegAndLogEmployer_to_RegEmployer)
        }
        view.findViewById<Button>(R.id.regAndLog_employer_second_button).setOnClickListener {
            findNavController().navigate(R.id.action_RegAndLogEmployer_to_LogEmployer)
        }
    }
}