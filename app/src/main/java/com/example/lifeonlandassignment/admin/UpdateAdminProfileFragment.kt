package com.example.lifeonlandassignment.admin

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
import com.example.lifeonlandassignment.databinding.AdminUpdateProfileScreenBinding
import com.example.lifeonlandassignment.user.UpdateProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

class UpdateAdminProfileFragment : Fragment() {
    private lateinit var updateAdminProfileViewModel: UpdateAdminProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AdminUpdateProfileScreenBinding = DataBindingUtil.inflate(
            inflater, R.layout.admin_update_profile_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = UpdateAdminProfileViewModelFactory(repository, application)

        updateAdminProfileViewModel = ViewModelProvider(this, factory).get(UpdateAdminProfileViewModel::class.java)
        binding.updateAdminProfileViewModel = updateAdminProfileViewModel
        binding.lifecycleOwner = this

        // Initialize the back button ImageView
        val backButton: ImageView = binding.backButtonAdminProfile
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        binding.txtInputAdminID.text = runBlocking { updateAdminProfileViewModel.getAdmin(Global.loginUser)?.adminId.toString() }
        binding.txtUpdateProfileInputUsernameAdmin.text = runBlocking { updateAdminProfileViewModel.getAdmin(Global.loginUser)?.adminUsername }

        updateAdminProfileViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

}