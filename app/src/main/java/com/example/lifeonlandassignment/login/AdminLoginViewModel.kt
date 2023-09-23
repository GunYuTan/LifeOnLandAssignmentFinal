package com.example.lifeonlandassignment.login

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AdminLoginViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _navigatetoHome = MutableLiveData<Boolean>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val navigatetoUserDetails: LiveData<Boolean>
        get() = _navigatetoHome

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPass = MutableLiveData<String>()

    fun loginButton(){
        val username = inputUsername.value
        val password = inputPass.value

        if(username == null || password == null){
            _messageLiveData.value = "Each field is required."
        }else{
            uiScope.launch {
                val userList = repository.getAdminUsername(username!!)
                if(userList != null){
                    if(userList.adminPassword == password){
                        inputUsername.value == null
                        inputPass.value == null
                        _messageLiveData.value = "Login Successfully."
                        _navigatetoHome.value = true
                        Global.loginUser = username
                    }else{
                        _messageLiveData.value = "Invalid username or password."
                    }
                }
                else{
                    _messageLiveData.value = "Invalid username or password."
                }
            }
        }
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}