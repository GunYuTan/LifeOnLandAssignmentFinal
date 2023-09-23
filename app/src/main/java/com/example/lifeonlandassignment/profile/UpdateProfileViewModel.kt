package com.example.lifeonlandassignment.profile

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UpdateProfileViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    @Bindable
    val inputPhoneNo = MutableLiveData<String>()

    @Bindable
    val inputNewPass = MutableLiveData<String>()

    @Bindable
    val inputConfirmNewPass = MutableLiveData<String>()

    @Bindable
    val inputCurrentPass = MutableLiveData<String>()

    fun updateButton(){
        val phoneNo = inputPhoneNo.value
        val newPass = inputNewPass.value
        val confirmNewPass = inputConfirmNewPass.value
        val currentPass = inputCurrentPass.value
        var message: String = ""
        var phoneChange = false
        var passwordChange = false

        uiScope.launch {
            val userList = repository.getUsername(Global.loginUser)
            if(userList?.password == currentPass){
                if(phoneNo != null && userList?.phoneNo != phoneNo){
                    message += "Phone Number, "
                    phoneChange = true
                }

                if(newPass != null && userList?.password != newPass){
                    if(newPass == confirmNewPass){
                        message += "Password,"
                        passwordChange = true
                    }else{
                        _messageLiveData.value = "Password field are not match."
                    }
                }

                if(message != ""){
                    if(phoneChange && passwordChange){
                        repository.update(User(userList?.userId, userList!!.username, newPass!!, phoneNo!!, userList!!.email, userList?.userImage))
                    }else if(phoneChange){
                        repository.update(User(userList?.userId, userList!!.username, userList!!.password, phoneNo!!, userList!!.email, userList?.userImage))
                    }else if(passwordChange){
                        repository.update(User(userList?.userId, userList!!.username, newPass!!, userList!!.phoneNo, userList!!.email, userList?.userImage))
                    }
                    _messageLiveData.value = message + "update successfully."
                }else{
                    _messageLiveData.value = "Nothing changes."
                }
            }else{
                _messageLiveData.value = "Invalid Current Password."
            }

        }
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}