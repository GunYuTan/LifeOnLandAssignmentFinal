package com.example.lifeonlandassignment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.lifeonlandassignment.R

class UpdateEventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.update_event_screen, container, false)

        // Initialize the back button ImageView
        val backButton = view.findViewById<ImageView>(R.id.backButtonUpdateEventData)
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }
        return view
    }
}