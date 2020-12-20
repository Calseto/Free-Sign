package com.freesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home_screen.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listkategori= arrayOf<String>("apel","mangga","jeruk")
        val spinner = homescreen_spinner
        if (spinner != null) {
            val adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item, listkategori
            )
            spinner.adapter = adapter
        }



    }
}