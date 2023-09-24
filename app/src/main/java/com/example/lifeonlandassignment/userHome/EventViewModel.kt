package com.example.lifeonlandassignment.userHome

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _navigatetoCurrentlyEvent = MutableLiveData<Boolean>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val navigatetoCurrentEvent: LiveData<Boolean>
        get() = _navigatetoCurrentlyEvent
    fun checkCurrentEvent(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        uiScope.launch {
            val eventList = repository.getHappenEvent(dateFormat.format(currentDate))
            if(eventList == null){
                _messageLiveData.value = "Currently no event."
            }else {
                _navigatetoCurrentlyEvent.value = true
            }
        }

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}