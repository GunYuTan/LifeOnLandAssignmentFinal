package com.example.lifeonlandassignment.userHome

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.manage.AddEventViewModel
import java.lang.IllegalArgumentException

class DonationViewModelFactory   (private  val repository: AssignmentDatabaseRepository,
                                  private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DonationViewModel::class.java)) {
            return DonationViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}