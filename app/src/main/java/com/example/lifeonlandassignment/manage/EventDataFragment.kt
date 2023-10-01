package com.example.lifeonlandassignment.manage

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.example.lifeonlandassignment.databinding.EventDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyEventItem
import com.example.lifeonlandassignment.userHome.RecyclerViewEventAdapter
import kotlinx.coroutines.runBlocking

class EventDataFragment : Fragment() {
    private lateinit var  eventDataViewModel: EventDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: EventDataScreenBinding = DataBindingUtil.inflate(inflater, R.layout.event_data_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = EventDataViewModelFactory(repository, application)
        eventDataViewModel = ViewModelProvider(this,factory).get(EventDataViewModel::class.java)
        binding.eventDataViewModel = eventDataViewModel
        binding.lifecycleOwner = this

        val button: Button = binding.btnAddEvent
        button.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AddEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        // Initialize the back button ImageView
        val backButton = binding.backButtonEventData
        // Set click listener for back button
        backButton.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AdminHomeFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        // Initialize RecyclerView
        val eventList = runBlocking { eventDataViewModel.getEventList() }
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val myItemList: List<MyEventItem> = eventList.map { event ->
            MyEventItem(
                EventID = event.eventId,
                imageResource = R.drawable.ic_event,
                EventName = "Event Name: ",
                EventNameDetail = event.eventName,
                EventStartDate = "Start Date : ",
                EventStartDateDetail = event.eventStartDate,
                EventEndDate = "End Date : ",
                EventEndDateDetail = event.eventEndDate,
                EventTotalDonation = "Donation Amount : ",
                EventTotalDonationDetail = event.eventDonation.toString(),
                imageResourceEditor = R.drawable.ic_editor,
                imageResourceDelete = R.drawable.ic_delete
            )
        }

        val adapter = RecyclerViewEventAdapter(myItemList, eventDataViewModel)
        recyclerView.adapter = adapter

        eventDataViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
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
        eventDataViewModel.showAlertDialog.observe(viewLifecycleOwner) { shouldShowDialog ->
            // Show the AlertDialog here
            if(shouldShowDialog){
                showConfirmationDialog()
            }
        }

        eventDataViewModel.refreshEventDataScreen.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, EventDataFragment())
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
                eventDataViewModel.deleteButton(Global.editEventId)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // User canceled, do nothing
                dialog.dismiss()
            }
            .create()
            .show()
    }
}