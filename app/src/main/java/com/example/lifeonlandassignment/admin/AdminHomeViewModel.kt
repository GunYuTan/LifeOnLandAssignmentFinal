package com.example.lifeonlandassignment.admin

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.Admin
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AdminHomeViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application),
    Observable {
    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String>
        get() = _messageLiveData
    private val context = application.applicationContext
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    suspend fun updateAdminProfileImage(uri: Uri){
        if(uri != null){
            val imageFilePath = saveImageToFile(uri, Global.loginUser)
            if(imageFilePath != null){
                Log.i("Testing", imageFilePath + "imagePath1")
                repository.updateAdminProfilePic(Global.loginUser, imageFilePath)
                Log.i("Testing", "Update Success!")
            }
        }
    }
    fun getUserImagePath(username: String) {
        uiScope.launch {
            _messageLiveData.value = repository.getLoginAdminImage(username)
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
        return repository.getAdminUsername(username)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}