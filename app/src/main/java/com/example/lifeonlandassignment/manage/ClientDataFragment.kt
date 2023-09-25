package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.databinding.ClientDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyClientItem
import com.example.lifeonlandassignment.userHome.RecyclerViewClientAdapter

class ClientDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClientDataScreenBinding.inflate(inflater, container, false)

        // Initialize the back button ImageView using binding
        val backButton = binding.backButtonClientData
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
            MyClientItem(R.drawable.ic_account,
                "User ID : ", "U0001",
                "Username : ", "SMALL",
                "Email : ", "small@gmail.com",
                "Phone : ", "small",),
            MyClientItem(R.drawable.ic_account,
                "User ID : ", "U0002",
                "Username : ", "BIG",
                "Email : ", "big@gmail.com",
                "Phone : ", "big",)
        )

        val adapter = RecyclerViewClientAdapter(items)
        recyclerView.adapter = adapter

        return view
    }
}