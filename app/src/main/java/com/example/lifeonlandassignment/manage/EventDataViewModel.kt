package com.example.lifeonlandassignment.manage

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EventDataViewModel  (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable{
    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val context = application.applicationContext
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val _showAlertDialog = MutableLiveData<Boolean>()
    val showAlertDialog: LiveData<Boolean>
        get() = _showAlertDialog

    private val _refreshEventDataScreen = MutableLiveData<Boolean>()
    val refreshEventDataScreen: LiveData<Boolean>
        get() = _refreshEventDataScreen

    suspend fun getEventList(): List<Event> {
        return repository.getAllEvent()
    }

    fun hideAlertDialog(){
        _showAlertDialog.value = false
    }
    fun showAlertDialog(){
        _showAlertDialog.value = true
    }

    fun deleteButton(deleteEvent: Int){
        val deleteEvent = runBlocking { repository.getEventId(deleteEvent) }
        if(deleteEvent!!.eventDonation > 0.00){
            _messageLiveData.value = "This event cannot be deleted."
        }else {
            uiScope.launch {
                repository.deleteEvent(deleteEvent.eventId!!)
                _refreshEventDataScreen.value = true
                _messageLiveData.value = "This event successfully deleted."

            }
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}