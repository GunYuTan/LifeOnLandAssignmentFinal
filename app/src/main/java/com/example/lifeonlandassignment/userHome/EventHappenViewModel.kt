package com.example.lifeonlandassignment.userHome

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event

class EventHappenViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {

    suspend fun getHappenEvent(startDate: String): Event?{
        Log.i("Testing", "123")
        return repository.getHappenEvent(startDate)
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}