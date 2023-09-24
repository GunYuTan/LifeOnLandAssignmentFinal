package com.example.lifeonlandassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment

class DonationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.donation_screen, container, false)

        // Initialize Spinner
        val spinner: Spinner = view.findViewById(R.id.spinner)

        // Initialize Adapter
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_data,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set Adapter to Spinner
        spinner.adapter = adapter

        // Item Selection Handling
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                // Do something with selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected
            }
        }

        // Initialize Back Button
        val backButton: ImageView = view.findViewById(R.id.backButtonDonation)

        // Set Click Listener for Back Button
        backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        return view
    }
}