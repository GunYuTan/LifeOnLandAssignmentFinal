package com.example.lifeonlandassignment.userHome

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository

class NotificationViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}