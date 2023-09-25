package com.example.lifeonlandassignment.userHome

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event

class UpcomingEventViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {

    suspend fun getUpcomingEvent(startDate: String): Event?{
        Log.i("Testing", "123")
        return repository.getUpcomingEvent(startDate)
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}