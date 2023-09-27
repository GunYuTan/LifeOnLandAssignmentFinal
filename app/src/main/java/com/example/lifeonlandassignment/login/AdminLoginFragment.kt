package com.example.lifeonlandassignment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.admin.AdminHomeFragment
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.AdminLoginScreenBinding

class AdminLoginFragment : Fragment() {
    private lateinit var adminLoginViewModel: AdminLoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AdminLoginScreenBinding = DataBindingUtil.inflate(inflater, R.layout.admin_login_screen,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = AdminLoginViewModelFactory(repository, application)

        adminLoginViewModel = ViewModelProvider(this, factory).get(AdminLoginViewModel::class.java)
        binding.adminLoginViewModel = adminLoginViewModel
        binding.lifecycleOwner = this

        // Initialize the back button ImageView
        val backButton: ImageView = binding.backButtonAdminLogin
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        adminLoginViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        adminLoginViewModel.navigatetoUserDetails.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, AdminHomeFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })

        return binding.root
    }
}