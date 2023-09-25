package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.ClientDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyClientItem
import com.example.lifeonlandassignment.userHome.MyEventItem
import com.example.lifeonlandassignment.userHome.RecyclerViewClientAdapter
import kotlinx.coroutines.runBlocking

class ClientDataFragment : Fragment() {
    private lateinit var clientDataViewModel: ClientDataViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ClientDataScreenBinding = DataBindingUtil.inflate(inflater, R.layout.client_data_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = ClientDataViewModelFactory(repository, application)
        clientDataViewModel = ViewModelProvider(this,factory).get(ClientDataViewModel::class.java)
        binding.clientDataViewModel = clientDataViewModel
        binding.lifecycleOwner = this

        // Initialize the back button ImageView using binding
        val backButton = binding.backButtonClientData
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }


        // Initialize RecyclerView
        val clientList = runBlocking { clientDataViewModel.getUserList() }
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val myItemList: List<MyClientItem> = clientList.map { client ->
            MyClientItem(
            imageResource = R.drawable.ic_account,
            ClientID = "User ID : ",
            ClientIDDetail = client.userId.toString(),
            ClientUsername = "Username : ",
            ClientUsernameDetail = client.username,
            ClientEmail = "Email : ",
            ClientEmailDetail = client.email,
            ClientPhone = "Phone : ",
            ClientPhoneDetail = client.phoneNo
            )
        }

        val adapter = RecyclerViewClientAdapter(myItemList)
        recyclerView.adapter = adapter

        return binding.root
    }
}