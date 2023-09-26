package com.example.lifeonlandassignment.manage

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.admin.AdminHomeFragment
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.NotificationScreenBinding
import com.example.lifeonlandassignment.databinding.ReportDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyEventItem
import com.example.lifeonlandassignment.userHome.MyNotificationItem
import com.example.lifeonlandassignment.userHome.RecyclerViewNotificationAdapter
import kotlinx.coroutines.runBlocking

class ReportDataFragment : Fragment() {
    private lateinit var  reportDataViewModel: ReportDataViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ReportDataScreenBinding = DataBindingUtil.inflate(inflater, R.layout.report_data_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = ReportDataViewModelFactory(repository, application)
        reportDataViewModel = ViewModelProvider(this,factory).get(ReportDataViewModel::class.java)
        binding.reportDataViewModel = reportDataViewModel
        binding.lifecycleOwner = this

        val backButton: ImageView = binding.backButtonNotificationData
        // Set click listener for back button
        backButton.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AdminHomeFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val button: Button = binding.btnAddNotification
        button.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AddNotificationFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        // Initialize RecyclerView
        val notiList = runBlocking { reportDataViewModel.getNotiList() }
        val recyclerView: RecyclerView = binding.recyclerNotificationView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val myItemList: List<MyNotificationItem> = notiList.map { noti ->
            MyNotificationItem(
                invisibleNotiId = noti.notiId!!,
                imageResource = R.drawable.ic_notification,
                notificationTitle = noti.notiTitle,
                notificationDetail = noti.notiDescription,
                imageResourceDelete = R.drawable.ic_delete
            )
        }


        val adapter = RecyclerViewNotificationAdapter(myItemList, reportDataViewModel)
        recyclerView.adapter = adapter

        reportDataViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the LiveData in the ViewModel
        reportDataViewModel.showAlertDialog.observe(viewLifecycleOwner) { shouldShowDialog ->
            // Show the AlertDialog here
            if(shouldShowDialog){
                showConfirmationDialog()
            }
        }

        reportDataViewModel.refreshEventDataScreen.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, ReportDataFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Delete") { _, _ ->
                // User confirmed, proceed with deletion
                // Call a ViewModel function to handle the deletion
                Log.i("DeleteEventId", Global.editEventId.toString())
                reportDataViewModel.deleteNotiButton(Global.editEventId)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // User canceled, do nothing
                dialog.dismiss()
            }
            .create()
            .show()
    }
}