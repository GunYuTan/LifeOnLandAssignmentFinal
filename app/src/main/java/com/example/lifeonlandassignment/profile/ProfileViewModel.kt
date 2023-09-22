package com.example.lifeonlandassignment.profile

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ProfileViewModel (private val repository: AssignmentDatabaseRepository, application: Application): AndroidViewModel(application){

    private val requestCodeSelectImage = 100
    private val context = application.applicationContext

    val selectedImageUri = MutableLiveData<Uri>()

    fun updateProfileImage(){
        val uri = selectedImageUri.value
        if(uri != null){
            val imageFilePath = saveImageToFile(uri, Global.loginUser)

        }
    }

    private fun saveImageToFile(uri: Uri, username: String): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "$username.jpg")
        val outputStream = FileOutputStream(file)
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
        }
        return file.absolutePath
    }
}