package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.databinding.NotificationScreenBinding
import com.example.lifeonlandassignment.databinding.ReportDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyNotificationItem
import com.example.lifeonlandassignment.userHome.RecyclerViewNotificationAdapter

class ReportDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ReportDataScreenBinding.inflate(inflater, container, false)

        val backButton: ImageView = binding.backButtonReportData
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        // Get root view
        val view = binding.root

        // Initialize RecyclerView
        val recyclerView: RecyclerView = binding.recyclerNotificationView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Sample data and setting adapter
        val items = listOf(
            MyNotificationItem(R.drawable.ic_notification,
                "Sunda Island Event is Happening!!", "lsdmaspmdiiamdinawiomsfkanfoiganifnaifna",
                R.drawable.ic_delete),
            MyNotificationItem(R.drawable.ic_notification,
                "Sunda Island Event is Happening!!", "sfdkoakfsmkvnaionfsanfoianfskfnaipfasmfian",
                R.drawable.ic_delete,)
        )

        val adapter = RecyclerViewNotificationAdapter(items)
        recyclerView.adapter = adapter

        return view
    }
}