package com.example.lifeonlandassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.DonationScreenBinding
import com.example.lifeonlandassignment.userHome.DonationViewModel
import com.example.lifeonlandassignment.userHome.DonationViewModelFactory
import kotlinx.coroutines.runBlocking

class DonationFragment : Fragment() {
    private lateinit var donationViewModel: DonationViewModel
    private var _binding: DonationScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.donation_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = DonationViewModelFactory(repository, application)
        donationViewModel = ViewModelProvider(this,factory).get(DonationViewModel::class.java)
        binding.donationViewModel = donationViewModel
        binding.lifecycleOwner = this

        // Initialize Spinner
        val spinner: Spinner = binding.spinner

        // Initialize Adapter
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_data,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set Adapter to Spinner
        spinner.adapter = adapter

        // Item Selection Handling
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                donationViewModel.inputDonationMethod = selectedItem// Do something with selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected
            }
        }

        // Initialize Back Button
        val backButton: ImageView = binding.backButtonDonation

        // Set Click Listener for Back Button
        backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        binding.txtInputUserID.text = runBlocking { donationViewModel.getUsername(Global.loginUser)!!.userId.toString() }
        binding.txtInputEventIDDonation.text = Global.donationEventId.toString()

        donationViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}