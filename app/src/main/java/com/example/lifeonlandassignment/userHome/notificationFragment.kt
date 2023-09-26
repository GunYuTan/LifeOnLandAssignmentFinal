package com.example.lifeonlandassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifeonlandassignment.databinding.NotificationScreenBinding
import com.example.lifeonlandassignment.userHome.MyNotificationCustomerItem
import com.example.lifeonlandassignment.userHome.NotificationViewModel
import com.example.lifeonlandassignment.userHome.RecyclerViewNotificationCustomerAdapter

class NotificationFragment : Fragment() {
    private lateinit var notificationViewModel: NotificationViewModel
    private var _binding: NotificationScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotificationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        val items = listOf(
            MyNotificationCustomerItem(R.drawable.ic_notification,
                "Sunda Island Event is Happening!!", "Some content here"),
            MyNotificationCustomerItem(R.drawable.ic_notification,
                "Another Event is Happening!!", "Another content here")
        )

        binding.recyclerNotificationView.layoutManager = LinearLayoutManager(context)
        binding.recyclerNotificationView.adapter = RecyclerViewNotificationCustomerAdapter(items)
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
