package com.example.lifeonlandassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.databinding.NotificationScreenBinding
import com.example.lifeonlandassignment.userHome.MyNotificationItem
import com.example.lifeonlandassignment.userHome.RecyclerViewNotificationAdapter

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = NotificationScreenBinding.inflate(inflater, container, false)

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