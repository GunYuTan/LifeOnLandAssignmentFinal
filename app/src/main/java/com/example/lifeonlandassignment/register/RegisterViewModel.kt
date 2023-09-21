package com.example.lifeonlandassignment.register

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.User
import kotlinx.coroutines.*

class RegisterViewModel(private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {

    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val users = repository.userList

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPass = MutableLiveData<String>()

    @Bindable
    val inputConfirmPass = MutableLiveData<String>()

    @Bindable
    val inputPhoneNo = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    fun registerButton(){
        val username = inputUsername.value
        val password = inputPass.value
        val confirmPass = inputConfirmPass.value
        val phoneNo = inputPhoneNo.value
        val email = inputEmail.value

        //Log.i("Testing",inputUsername.value.toString())
        if(username == null || password == null || confirmPass == null || phoneNo == null || email == null){
            _messageLiveData.value = "Each field is required."
        }else if(password != confirmPass){
            _messageLiveData.value = "Password field is not match."
        }
        else{
            uiScope.launch {
                val userList = repository.getUsername(username!!)
                if(userList !=null){
                    _messageLiveData.value = "Username has been register before."
                }else{
                    repository.insert(User(null,username, password, phoneNo, email, null))
                    inputUsername.value = null
                    inputPass.value = null
                    inputConfirmPass.value = null
                    inputPhoneNo.value = null
                    inputEmail.value = null
                    _messageLiveData.value = "Register successfully."
                }
            }
        }
    }

    fun backToLogin(){

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}

