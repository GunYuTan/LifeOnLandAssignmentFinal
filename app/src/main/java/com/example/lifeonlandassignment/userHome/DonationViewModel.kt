package com.example.lifeonlandassignment.userHome

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
import com.example.lifeonlandassignment.database.Donation
import com.example.lifeonlandassignment.database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DonationViewModel  (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    @Bindable
    val inputEventId = Global.donationEventId

    @Bindable
    val inputUserId = runBlocking { repository.getUsername(Global.loginUser)!!.userId }

    @Bindable
    val inputDonationAmount = MutableLiveData<String>()

    var inputDonationMethod: String = ""

    fun donation(){
        val donationAmount = inputDonationAmount.value!!.toDouble()
        if(donationAmount == 0.00 || inputDonationMethod == null){
            _messageLiveData.value = "Each field is required."
        }else{
            uiScope.launch {
                Log.i("Donation", inputUserId.toString() + inputEventId.toString() + donationAmount.toString() + inputDonationMethod + "")
                repository.insert(Donation(null, inputUserId!!, inputEventId, donationAmount!!, inputDonationMethod))
                repository.updateEventDonation(inputEventId, donationAmount)
                inputDonationAmount.value = null
                _messageLiveData.value = "Donation successfully added."

            }
        }
    }

    suspend fun getUsername(username: String) : User?{
        Log.i("Testing", "0")
        return repository.getUsername(username)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}