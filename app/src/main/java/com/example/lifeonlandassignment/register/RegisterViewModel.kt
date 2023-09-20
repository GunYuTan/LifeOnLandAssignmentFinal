package com.example.lifeonlandassignment.register

import android.app.Application
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository

class RegisterViewModel(private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {

    private val _messageLiveData = MutableLiveData<String>()
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
        if(inputUsername == null || inputPass == null || inputConfirmPass == null || inputPhoneNo == null || inputEmail == null){
            _messageLiveData.value = "Each field is required!"
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}

