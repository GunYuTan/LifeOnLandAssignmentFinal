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
import com.example.lifeonlandassignment.databinding.DonationDataScreenBinding
import com.example.lifeonlandassignment.userHome.MyClientItem
import com.example.lifeonlandassignment.userHome.MyDonationItem
import com.example.lifeonlandassignment.userHome.RecyclerViewDonationAdapter
import kotlinx.coroutines.runBlocking

class DonationDataFragment : Fragment() {
    private lateinit var donationDataViewModel: DonationDataViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DonationDataScreenBinding = DataBindingUtil.inflate(inflater, R.layout.donation_data_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = DonationDataViewModelFactory(repository, application)
        donationDataViewModel = ViewModelProvider(this,factory).get(DonationDataViewModel::class.java)
        binding.donationDataViewModel = donationDataViewModel
        binding.lifecycleOwner = this

        // Initialize the back button ImageView
        val backButton = binding.backButtonDonationData
        // Set click listener for back button
        backButton.setOnClickListener {
            // Perform your action here, for example, navigate back
            fragmentManager?.popBackStack()
        }

        // Initialize RecyclerView
        val donationList = runBlocking { donationDataViewModel.getDonationList() }
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Sample data and setting adapter
//        val items = listOf(
//            MyDonationItem(,
//                , "D0001",
//                , "SMALL",
//                , "Sunda Island Tiger",
//                , "MYR 1111",),
//            MyDonationItem(R.drawable.ic_favourite,
//                "Donation ID : ", "D0002",
//                "User ID : ", "BIG",
//                "Event ID : ", "Sunda Island Tiger",
//                "Donation Amount : ", "MYR 2222",)
//        )
        val myItemList: List<MyDonationItem> = donationList.map { donation ->
            MyDonationItem(
            imageResource = R.drawable.ic_favourite,
            DonationID = "Donation ID : ",
            DonationIDDetail = donation.donateId.toString(),
            DonationUserID = "User ID : ",
            DonationUserIDDetail = donation.donateUserId.toString(),
            DonationEventID = "Event ID : ",
            DonationEventIDDetail = donation.donateEventId.toString(),
            DonationAmount = "Donation Amount : ",
            DonationAmountDetail = donation.donateAmount.toString()
            )
        }

        val adapter = RecyclerViewDonationAdapter(myItemList)
        recyclerView.adapter = adapter

        return binding.root
    }
}