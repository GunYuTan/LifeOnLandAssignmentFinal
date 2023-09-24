package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.databinding.EventDataScreenBinding
import com.example.lifeonlandassignment.register.RegisterFragment
import com.example.lifeonlandassignment.userHome.MyItem
import com.example.lifeonlandassignment.userHome.RecyclerViewAdapter

class EventDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = EventDataScreenBinding.inflate(inflater, container, false)

        val button: Button = binding.btnAddEvent
        button.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AddEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val button1: Button = binding.btnUpdateEvent
        button1.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, UpdateEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        // Initialize the back button ImageView
        val backButton = binding.backButtonEventData
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        // Get root view
        val view = binding.root

        // Initialize RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Sample data and setting adapter
        val items = listOf(
            MyItem(R.drawable.ic_event,
                "Event : ", "Sunda Island Tiger",
                "Start Date : ", "11/11/1111",
                "End Date : ", "11/11/1111",
                "Donation Amount : ", "MYR 1111",
                R.drawable.ic_editor),
            MyItem(R.drawable.ic_event,
                "Event : ", "Sunda Island Tiger",
                "Start Date : ", "22/22/2222",
                "End Date : ", "22/22/2222",
                "Donation Amount : ", "MYR 2222",
                R.drawable.ic_editor)
        )

        val adapter = RecyclerViewAdapter(items)
        recyclerView.adapter = adapter

        return view
    }
}