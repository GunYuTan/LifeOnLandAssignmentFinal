package com.example.lifeonlandassignment.manage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifeonlandassignment.DatePickerFragment
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.AddEventScreenBinding
import com.example.lifeonlandassignment.databinding.UpdateEventScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UpdateEventFragment : Fragment() {
    private val requestCodeSelectImage = 100
    private lateinit var updateEventViewModel: UpdateEventViewModel
    private var _binding: UpdateEventScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.update_event_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = UpdateEventViewModelFactory(repository, application)
        updateEventViewModel = ViewModelProvider(this,factory).get(UpdateEventViewModel::class.java)
        binding.updateEventViewModel = updateEventViewModel
        binding.lifecycleOwner = this

        binding.txtInputEventIDAddEvent2.text = Global.editEventId.toString()
        val eventList = runBlocking { updateEventViewModel.getEventId(Global.editEventId) }
        val editableEventName: Editable = Editable.Factory.getInstance().newEditable(eventList!!.eventName)
        val editableEventDescription: Editable = Editable.Factory.getInstance().newEditable(eventList!!.eventDescription)
//        val editableEventStartDate: Editable = Editable.Factory.getInstance().newEditable(eventList!!.eventStartDate)
//        val editableEventEndDate: Editable = Editable.Factory.getInstance().newEditable(eventList!!.eventEndDate)
        binding.txtInputEventName2.text = editableEventName
        binding.txtInputEventDescription2.text = editableEventDescription
        binding.txtInputStartDate2.text = eventList.eventStartDate
        binding.txtInputEndDate2.text = eventList.eventEndDate
        Glide.with(requireContext())
            .load(eventList.eventImage)
            .placeholder(R.drawable.sunde_island2)
            .into(binding.addPic2)

        // Initialize the back button ImageView
        val backButton = binding.backButtonUpdateEventData
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        updateEventViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPic2.setOnClickListener {
            selectPicture()
        }

        binding.btnStartdateCalendar2.setOnClickListener{
            showStartDatePickerDialog()
        }

        binding.btnEnddateCalendar.setOnClickListener {
            showEndDatePickerDialog()
        }

        binding.btnUpdateExistEvent.setOnClickListener {
            updateEventViewModel.updateEventButton(binding.txtInputEventName2.text.toString(),
                binding.txtInputEventDescription2.text.toString(),
                binding.txtInputStartDate2.text.toString(), binding.txtInputEndDate2.text.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            Log.i("Testing", "onActivityResult1")
            if (requestCode == requestCodeSelectImage && resultCode == Activity.RESULT_OK && data != null) {
                val selectedImageUri = data?.data
                selectedImageUri?.let { uri ->
                    uiScope.launch {
                        // Pass the selected image URI to the ViewModel
                        Log.i("Testing", "onActivityResult2")
                        updateEventViewModel.inputEventImage = updateEventViewModel.getImageFilePath(uri)

                        Glide.with(requireContext())
                            .load(uri)
                            .placeholder(R.drawable.sunde_island2)
                            .into(binding.addPic2)
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }catch (e: Exception){
            Log.e("Error", "Error message", e)
        }

    }

    private fun selectPicture() {
        try {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            Log.i("Testing", "selectProfile1")
            intent.type = "image/*"
            Log.i("Testing", "selectProfile2")
            startActivityForResult(intent, requestCodeSelectImage)
            Log.i("Testing", "selectProfile3")
        }catch (e: Exception){
            Log.e("Error", "Error message", e)
        }

    }
    private fun showStartDatePickerDialog(){
        val datePickerFragment = DatePickerFragment { selectedDate ->
            // Update the date text view with the selected date
            binding.txtInputStartDate2.text = "$selectedDate"
            Toast.makeText(requireContext(), "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    private fun showEndDatePickerDialog(){
        val datePickerFragment = DatePickerFragment { selectedDate ->
            // Update the date text view with the selected date
            binding.txtInputEndDate2.text = "$selectedDate"
            Toast.makeText(requireContext(), "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }
        datePickerFragment.show(childFragmentManager, "datePicker")
    }
}