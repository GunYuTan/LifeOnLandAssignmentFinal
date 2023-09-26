package com.example.lifeonlandassignment.manage

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.Admin
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddNotificationViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    @Bindable
    val inputNotiTitle = MutableLiveData<String>()

    @Bindable
    val inputNotiDescription = MutableLiveData<String>()

    fun addNoti(){
        val notiTitle = inputNotiTitle.value
        val notiDescription = inputNotiDescription.value
        if(notiTitle == null || notiDescription == null){
            _messageLiveData.value = "Each field is required."
        }else{
            uiScope.launch {
                val adminId = getAdmin(Global.loginUser)
                repository.insert(Notification(null, adminId!!.adminId!!, notiTitle, notiDescription))
                inputNotiTitle.value = null
                inputNotiDescription.value = null
                _messageLiveData.value = "Notification has been added."
            }
        }
    }

    suspend fun getAdmin(username: String) : Admin?{
        Log.i("Testing", "0")
        return repository.getAdminUsername(username)
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}