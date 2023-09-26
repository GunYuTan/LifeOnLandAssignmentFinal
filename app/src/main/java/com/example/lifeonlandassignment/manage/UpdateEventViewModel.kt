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
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class UpdateEventViewModel  (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val context = application.applicationContext
    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    @Bindable
    var inputEventImage: String? = ""

    suspend fun getEventId(eventId: Int): Event?{
        return repository.getEventId(eventId)
    }

    fun getImageFilePath(uri: Uri): String{
        return saveImageToFile(uri)
    }


    fun updateEventButton(eventName: String?, eventDescription: String?, eventStartDate: String?, eventEndDate: String?){
        val eventImage: String? = inputEventImage
        Log.i("UpdateEvent",eventName + eventDescription + eventStartDate + eventEndDate + eventImage)
        if(eventName == null || eventDescription == null || eventStartDate == null || eventEndDate == null){
            Log.i("Testing", eventName + eventDescription + eventStartDate +eventEndDate)
            _messageLiveData.value = "Each field is required."
        }else if(eventImage != null){
            uiScope.launch {
                repository.updateEventNoImage(
                    eventName,
                    eventDescription,
                    eventStartDate,
                    eventEndDate,
                    Global.editEventId
                )
                _messageLiveData.value = "Event update successfully."
            }
        }else{
            uiScope.launch {
                repository.updateEvent(
                    eventName,
                    eventDescription,
                    eventStartDate,
                    eventEndDate,
                    eventImage!!,
                    Global.editEventId
                )
                _messageLiveData.value = "Event update successfully."
            }
        }

    }

    private fun saveImageToFile(uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
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
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}