package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.AddNotificationScreenBinding
import com.example.lifeonlandassignment.databinding.ReportDataScreenBinding

class AddNotificationFragment : Fragment() {
    private  lateinit var addNotificationViewModel: AddNotificationViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddNotificationScreenBinding = DataBindingUtil.inflate(inflater, R.layout.add_notification_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = AddNotificationViewModelFactory(repository, application)
        addNotificationViewModel = ViewModelProvider(this,factory).get(AddNotificationViewModel::class.java)
        binding.addNotificationViewModel = addNotificationViewModel
        binding.lifecycleOwner = this

        binding.backButtonAddEventData2.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        addNotificationViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}