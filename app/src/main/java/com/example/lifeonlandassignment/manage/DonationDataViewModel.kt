package com.example.lifeonlandassignment.manage

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Donation
import com.example.lifeonlandassignment.database.User

class DonationDataViewModel  (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {

    suspend fun getDonationList():List<Donation>{
        return repository.getAllDonation()
    }

    suspend fun getUserId(userId: Int): User?{
        return repository.getUserId(userId)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}