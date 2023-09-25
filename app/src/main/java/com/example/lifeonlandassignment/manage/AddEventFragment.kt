package com.example.lifeonlandassignment.manage

import android.app.Activity
import android.content.Intent
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
import com.bumptech.glide.Glide
import com.example.lifeonlandassignment.DatePickerFragment
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.AddEventScreenBinding
import com.example.lifeonlandassignment.user.ProfileViewModel
import com.example.lifeonlandassignment.user.ProfileViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddEventFragment : Fragment() {
    private val requestCodeSelectImage = 100
    private lateinit var addEventViewModel: AddEventViewModel
    private var _binding: AddEventScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.add_event_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = AddEventViewModelFactory(repository, application)
        addEventViewModel = ViewModelProvider(this,factory).get(AddEventViewModel::class.java)
        binding.addEventViewModel = addEventViewModel
        binding.lifecycleOwner = this

        // Initialize the back button ImageView
        val backButton: ImageView = binding.backButtonAddEventData
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        binding.txtInputAdminIDAddEvent.text = runBlocking { addEventViewModel.getAdmin(Global.loginUser)?.adminId.toString() }

        addEventViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPic.setOnClickListener {
            selectPicture()
        }

        binding.btnStartdateCalendar.setOnClickListener{
            showStartDatePickerDialog()
        }

        binding.btnEnddateCalendar2.setOnClickListener {
            showEndDatePickerDialog()
        }

        binding.btnAddNewEvent.setOnClickListener {
            addEventViewModel.addEventButton(runBlocking { addEventViewModel.getAdmin(Global.loginUser)?.adminId.toString() })
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
                        addEventViewModel.inputEventImage = addEventViewModel.getImageFilePath(uri)

                        Glide.with(requireContext())
                            .load(uri)
                            .placeholder(R.drawable.sunde_island2)
                            .into(binding.addPic)
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
            binding.txtInputStartDate.text = "$selectedDate"
            Toast.makeText(requireContext(), "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    private fun showEndDatePickerDialog(){
        val datePickerFragment = DatePickerFragment { selectedDate ->
            // Update the date text view with the selected date
            binding.txtInputEndDate.text = "$selectedDate"
            Toast.makeText(requireContext(), "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }
        datePickerFragment.show(childFragmentManager, "datePicker")
    }
}