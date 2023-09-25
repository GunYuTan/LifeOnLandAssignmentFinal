package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.EventDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyItem
import com.example.lifeonlandassignment.userHome.RecyclerViewAdapter
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


        // Initialize RecyclerView
        val eventList = runBlocking { eventDataViewModel.getEventList() }
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val myItemList: List<MyItem> = eventList.map { event ->
            MyItem(
                eventId = event.eventId,
                imageResource = R.drawable.ic_event,
                eventName = "Event Name: ",
                eventDetail = event.eventName,
                startDate = "Start Date : ",
                startDateDetail = event.eventStartDate,
                endDate = "End Date : ",
                endDateDetail = event.eventEndDate,
                donationAmount = "Donation Amount : ",
                donationAmountDetail = event.eventDonation.toString(),
                imageResourceEditor = R.drawable.ic_editor,
                imageResourceDelete = R.drawable.ic_delete
            )
        }

        val adapter = RecyclerViewAdapter(myItemList)
        recyclerView.adapter = adapter

        return binding.root
    }
}