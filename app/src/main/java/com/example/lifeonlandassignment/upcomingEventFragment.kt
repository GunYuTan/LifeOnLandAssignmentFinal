package com.example.lifeonlandassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
class UpcomingEventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_description_screen_1, container, false)
        // Initialize the back button ImageView
        val backButton = view.findViewById<ImageView>(R.id.backButtonUpcomingButton)
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }
        return view
    }
}