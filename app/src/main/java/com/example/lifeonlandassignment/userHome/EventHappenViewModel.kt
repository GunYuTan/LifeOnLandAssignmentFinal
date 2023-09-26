package com.example.lifeonlandassignment.userHome

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event
import com.example.lifeonlandassignment.database.JoinedEvent
import com.example.lifeonlandassignment.database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventHappenViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    suspend fun getHappenEvent(startDate: String): Event?{
        Log.i("Testing", "123")
        return repository.getHappenEvent(startDate)
    }

    private fun checkJoinEvent(userId: Int, eventId: Int): Boolean{
        val joinEvent = runBlocking { repository.getJoinEvent(userId, eventId) }
        return joinEvent != null
    }

    fun joinEvent(userId: Int, eventId: Int){
        val checkReturn = checkJoinEvent(userId, eventId)
        if(!checkReturn){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = Date()
            uiScope.launch{
                repository.insert(JoinedEvent(null, userId, eventId, dateFormat.format(currentDate).toString()))
                _messageLiveData.value = "Successfully joined this event."
            }
        }else{
            _messageLiveData.value = "You had already joined this event before."
        }
    }

    fun leaveEvent(userId: Int, eventId: Int){
        val checkReturn = checkJoinEvent(userId, eventId)
        if(checkReturn){
            uiScope.launch{
                repository.deleteEvent(userId,eventId)
                _messageLiveData.value = "Successfully leave this event."
            }
        }else{
            _messageLiveData.value = "You have not joined this event before."
        }
    }

    suspend fun getUsername(): User?{
        return repository.getUsername(Global.loginUser)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}