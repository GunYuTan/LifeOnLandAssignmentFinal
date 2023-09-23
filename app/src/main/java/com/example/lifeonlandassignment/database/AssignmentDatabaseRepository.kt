package com.example.lifeonlandassignment.database

import android.util.Log
import com.example.lifeonlandassignment.Global

class AssignmentDatabaseRepository (private val dao: AssignmentDatabaseDao){
    val userList = dao.getAllUser()

    suspend fun insert(user: User){
        return dao.insert(user)
    }

    suspend fun update(user: User){
        return dao.update(user)
    }

    suspend fun getUsername(username: String):User?{
        return dao.getUsername(username)
    }

    suspend fun updateProfilePic(username: String, userImage: String){
        return dao.updateProfilePic(username, userImage)
    }

    suspend fun getLoginUserImage(username: String): String?{
        Log.i("Testing", "Repo" + username)
        return dao.getLoginUserImage(username)
    }
}