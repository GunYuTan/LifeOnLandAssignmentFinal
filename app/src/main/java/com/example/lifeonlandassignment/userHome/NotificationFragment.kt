package com.example.lifeonlandassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.NotificationScreenBinding
import com.example.lifeonlandassignment.userHome.MyNotificationCustomerItem
import com.example.lifeonlandassignment.userHome.MyNotificationItem
import com.example.lifeonlandassignment.userHome.NotificationViewModel
import com.example.lifeonlandassignment.userHome.NotificationViewModelFactory
import com.example.lifeonlandassignment.userHome.RecyclerViewNotificationCustomerAdapter
import kotlinx.coroutines.runBlocking

class NotificationFragment : Fragment() {
    private lateinit var notificationViewModel: NotificationViewModel
    private var _binding: NotificationScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.notification_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = NotificationViewModelFactory(repository, application)
        notificationViewModel = ViewModelProvider(this,factory).get(NotificationViewModel::class.java)
        binding.notificationViewModel = notificationViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notiList = runBlocking { notificationViewModel.getNotiList() }
        binding.recyclerNotificationView.layoutManager = LinearLayoutManager(context)

        // Initialize RecyclerView
//        val items = listOf(
//            MyNotificationCustomerItem(,
//                "Sunda Island Event is Happening!!", "Some content here"),
//            MyNotificationCustomerItem(R.drawable.ic_notification,
//                "Another Event is Happening!!", "Another content here")
//        )
        val myItemList: List<MyNotificationCustomerItem> = notiList.map { noti ->
            MyNotificationCustomerItem(
                imageResource = R.drawable.ic_notification,
                notificationCustomerTitle = noti.notiTitle,
                notificationCustomerDetail = noti.notiDescription
            )
        }

        binding.recyclerNotificationView.adapter = RecyclerViewNotificationCustomerAdapter(myItemList)
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
