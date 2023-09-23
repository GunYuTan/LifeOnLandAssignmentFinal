package com.example.lifeonlandassignment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.UpdateProfileScreenBinding

class UpdateProfileFragment : Fragment() {
    private lateinit var  updateProfileViewModel: UpdateProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: UpdateProfileScreenBinding = DataBindingUtil.inflate(
            inflater, R.layout.update_profile_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = UpdateProfileViewModelFactory(repository, application)

        val backButton: ImageView = binding.backButtonUpdateProfile
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        updateProfileViewModel = ViewModelProvider(this, factory).get(UpdateProfileViewModel::class.java)
        binding.updateProfileViewModel = updateProfileViewModel
        binding.lifecycleOwner = this

        binding.txtUpdateProfileInputUsername.text = Global.loginUser

        updateProfileViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}