package com.example.lifeonlandassignment.admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.AdminHomeScreenBinding
import com.example.lifeonlandassignment.login.LoginFragment
import com.example.lifeonlandassignment.manage.ClientDataFragment
import com.example.lifeonlandassignment.manage.DonationDataFragment
import com.example.lifeonlandassignment.manage.EventDataFragment
import com.example.lifeonlandassignment.manage.ReportDataFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AdminHomeFragment : Fragment (){
    private val requestCodeSelectImage = 100
    private lateinit var adminHomeViewModel: AdminHomeViewModel
    private var _binding: AdminHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.admin_home_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = AdminHomeViewModelFactory(repository, application)
        adminHomeViewModel = ViewModelProvider(this,factory).get(AdminHomeViewModel::class.java)
        binding.adminHomeViewModel = adminHomeViewModel
        binding.lifecycleOwner = this

        adminHomeViewModel.getUserImagePath(Global.loginUser)
        adminHomeViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                //imagePath = message
                Glide.with(requireContext())
                    .load(message)
                    .into(binding.adminProfilePic)
            }
        })

        val tableLayout1 : TableRow = binding.btnClientDataScreen
        tableLayout1.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, ClientDataFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val tableLayout2 : TableRow = binding.btnEventDataScreen
        tableLayout2.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, EventDataFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val tableLayout3 : TableRow = binding.btnDonationDataScreen
        tableLayout3.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, DonationDataFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val tableLayout4 : TableRow = binding.btnReportDataScreen
        tableLayout4.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, ReportDataFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val tableLayout5 : TableRow = binding.btnLogout
        tableLayout5.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, LoginFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdateAdminProfileScreen.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, UpdateAdminProfileFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.btnChangeAdminPicture.setOnClickListener {
            Log.i("Testing", "Change Picture Button Pressed")
            selectProfilePicture()
        }

        binding.txtProfileUsername2.text = runBlocking { adminHomeViewModel.getAdmin(Global.loginUser)?.adminUsername }
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
                        adminHomeViewModel.updateAdminProfileImage(uri)

                        Glide.with(requireContext())
                            .load(uri)
                            .placeholder(R.drawable.profile_picture)
                            .into(binding.adminProfilePic)
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }catch (e: Exception){
            Log.e("Error", "Error message", e)
        }

    }

    private fun selectProfilePicture() {
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
}