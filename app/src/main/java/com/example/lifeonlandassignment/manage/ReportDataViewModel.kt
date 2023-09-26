package com.example.lifeonlandassignment.manage

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReportDataViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
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

    suspend fun getNotiList(): List<Notification> {
        return repository.getAllNotification()
    }

    fun showAlertDialog(){
        _showAlertDialog.value = true
    }

    fun deleteNotiButton(deleteEvent: Int) {
        uiScope.launch {
            repository.deleteNoti(Global.deleteNoti)
            _refreshEventDataScreen.value = true
            _messageLiveData.value = "This event successfully deleted."

        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}