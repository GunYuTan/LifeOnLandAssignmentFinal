package com.example.lifeonlandassignment.manage

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.Admin
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddEventViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val context = application.applicationContext
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    @Bindable
    val inputEventName = MutableLiveData<String>()

    @Bindable
    val inputEventDescription = MutableLiveData<String>()

    @Bindable
    val inputEventStartDate = MutableLiveData<String>()

    @Bindable
    val inputEventEndDate = MutableLiveData<String>()

    @Bindable
    var inputEventImage: String? = ""

    fun addEventButton(eventAdminId: String){
        val eventName = inputEventName.value
        val eventDescription = inputEventDescription.value
        val eventStartDate = inputEventStartDate.value
        val eventEndDate = inputEventEndDate.value
        val eventImage = inputEventImage

        if(eventAdminId == null || eventName == null || eventDescription == null || eventStartDate == null || eventEndDate == null || eventImage == null){
            _messageLiveData.value = "Each field is required."
        }else{
            uiScope.launch {
                repository.insert(Event(null, eventAdminId.toInt(), eventName, eventDescription, eventStartDate, eventEndDate, 0.00, eventImage))
                inputEventName.value = null
                inputEventDescription.value = null
                inputEventStartDate.value = null
                inputEventEndDate.value = null
                inputEventImage = null
                _messageLiveData.value = "Event add successfully."
            }
        }

    }

    private fun saveImageToFile(uri: Uri, username: String): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "${System.currentTimeMillis()}$username.jpg")
        val outputStream = FileOutputStream(file)
        try {
            inputStream?.use { input ->
                outputStream.use { output ->
                    val buffer = ByteArray(4 * 1024) // 4K buffer size
                    while (true) {
                        val bytesRead = input.read(buffer)
                        if (bytesRead == -1) break
                        output.write(buffer, 0, bytesRead)
                    }
                    output.flush()
                }
            }} catch (e: Exception) {
            Log.e("Error", "Error message", e)
        }
        Log.i("Testing", "saveImageFile1")
        return file.absolutePath
    }

    suspend fun getAdmin(username: String) : Admin?{
        Log.i("Testing", "0")
        return repository.getAdminUsername(username)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}