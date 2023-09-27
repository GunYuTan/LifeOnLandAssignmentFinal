package com.example.lifeonlandassignment.userHome

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import java.lang.IllegalArgumentException

class NotificationViewModelFactory (private  val repository: AssignmentDatabaseRepository,
                                    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}