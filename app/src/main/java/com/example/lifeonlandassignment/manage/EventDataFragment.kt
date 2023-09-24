package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.lifeonlandassignment.R

class EventDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_data_screen, container, false)

        val button: Button = view.findViewById(R.id.btnAddEvent)
        button.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AddEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val button1: Button = view.findViewById(R.id.btnUpdateEvent)
        button1.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, UpdateEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        // Initialize the back button ImageView
        val backButton = view.findViewById<ImageView>(R.id.backButtonEventData)
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }
        return view
    }
}