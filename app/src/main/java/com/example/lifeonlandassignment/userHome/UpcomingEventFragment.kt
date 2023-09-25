package com.example.lifeonlandassignment.userHome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event
import com.example.lifeonlandassignment.databinding.EventDescriptionScreen1Binding
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpcomingEventFragment : Fragment() {
    private lateinit var upcomingEventViewModel: UpcomingEventViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: EventDescriptionScreen1Binding = DataBindingUtil.inflate(
            inflater, R.layout.event_description_screen_1, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = UpcomingEventViewModelFactory(repository, application)

        upcomingEventViewModel = ViewModelProvider(this, factory).get(UpcomingEventViewModel::class.java)
        binding.upcomingEventViewModel = upcomingEventViewModel
        binding.lifecycleOwner = this

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        val eventList: Event? = runBlocking { upcomingEventViewModel.getUpcomingEvent(dateFormat.format(currentDate)) }
        Log.i("Testing", eventList!!.eventImage + "123")
        if(eventList != null){
            Glide.with(requireContext())
                .load(eventList.eventImage)
                .placeholder(R.drawable.sunde_island2)
                .into(binding.picUpcomingHarimau)

            binding.txtAnimalTitle.text = eventList.eventName
            binding.txtUpcoming.text = eventList.eventDescription
        }
        else{
            binding.picUpcomingHarimau.visibility = View.GONE
            binding.txtAnimalTitle.visibility = View.GONE
            binding.txtUpcoming.text = "Currently no upcoming event."
        }

        // Initialize the back button ImageView
        val backButton = binding.backButtonUpcomingButton
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, EventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return binding.root
    }
}