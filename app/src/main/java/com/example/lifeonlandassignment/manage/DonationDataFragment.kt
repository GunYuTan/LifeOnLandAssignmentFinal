package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.databinding.DonationDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyItem
import com.example.lifeonlandassignment.userHome.RecyclerViewAdapter

class DonationDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DonationDataScreenBinding.inflate(inflater, container, false)

        // Initialize the back button ImageView
        val backButton = binding.backButtonDonationData
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
            MyItem(R.drawable.ic_favourite,
                "Donation ID : ", "D0001",
                "Username : ", "SMALL",
                "Event : ", "Sunda Island Tiger",
                "Donation Amount : ", "MYR 1111",
                R.drawable.ic_none),
            MyItem(R.drawable.ic_favourite,
                "Donation ID : ", "D0002",
                "Username : ", "BIG",
                "Event : ", "Sunda Island Tiger",
                "Donation Amount : ", "MYR 2222",
                R.drawable.ic_none)
        )

        val adapter = RecyclerViewAdapter(items)
        recyclerView.adapter = adapter

        return view
    }
}