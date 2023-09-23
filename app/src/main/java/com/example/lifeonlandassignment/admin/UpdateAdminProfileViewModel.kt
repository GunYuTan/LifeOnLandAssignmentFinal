package com.example.lifeonlandassignment.admin

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.Admin
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UpdateAdminProfileViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
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
            val userList = repository.getAdminUsername(Global.loginUser)
            if(userList?.adminPassword == currentPass){
                if(phoneNo != null && userList?.adminPhoneNo != phoneNo){
                    message += "Phone Number, "
                    phoneChange = true
                }

                if(newPass != null && userList?.adminPassword != newPass){
                    if(newPass == confirmNewPass){
                        message += "Password,"
                        passwordChange = true
                    }else{
                        _messageLiveData.value = "Password field are not match."
                    }
                }

                if(message != ""){
                    if(phoneChange && passwordChange){
                        repository.update(Admin(userList?.adminId, userList!!.adminUsername, newPass!!, phoneNo!!, userList!!.adminEmail, userList?.adminUserImage))
                    }else if(phoneChange){
                        repository.update(Admin(userList?.adminId, userList!!.adminUsername, userList!!.adminPassword, phoneNo!!, userList!!.adminEmail, userList?.adminUserImage))
                    }else if(passwordChange){
                        repository.update(Admin(userList?.adminId, userList!!.adminUsername, newPass!!, userList!!.adminPhoneNo, userList!!.adminEmail, userList?.adminUserImage))
                    }
                    inputPhoneNo.value = null
                    inputNewPass.value = null
                    inputConfirmNewPass.value = null
                    inputCurrentPass.value = null
                    _messageLiveData.value = message + "update successfully."
                }else{
                    _messageLiveData.value = "Nothing changes."
                }
            }else{
                _messageLiveData.value = "Invalid Current Password."
            }

        }
    }

    suspend fun getAdmin(username: String) : Admin?{
        return repository.getAdminUsername(username)
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}