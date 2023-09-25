package com.example.lifeonlandassignment.database

import android.util.Log
import com.example.lifeonlandassignment.Global

class AssignmentDatabaseRepository (private val dao: AssignmentDatabaseDao){
    val userList = dao.getAllUser()
    suspend fun getAllEvent(): List<Event>{
        return dao.getAllEvent()
    }

    suspend fun insert(user: User){
        return dao.insert(user)
    }

    suspend fun insert(admin: Admin){
        return dao.insert(admin)
    }

    suspend fun insert(event: Event){
        return dao.insert(event)
    }

    suspend fun clearEvent(){
        return dao.clearEvent()
    }

    suspend fun update(user: User){
        return dao.update(user)
    }

    suspend fun update(admin: Admin){
        return dao.update(admin)
    }

    suspend fun getUsername(username: String):User?{
        return dao.getUsername(username)
    }

    suspend fun getAdminUsername(username: String):Admin?{
        return dao.getAdminUsername(username)
    }

    suspend fun updateProfilePic(username: String, userImage: String){
        return dao.updateProfilePic(username, userImage)
    }

    suspend fun updateAdminProfilePic(username: String, userImage: String){
        return dao.updateAdminProfilePic(username, userImage)
    }

    suspend fun getLoginUserImage(username: String): String?{
        return dao.getLoginUserImage(username)
    }

    suspend fun getLoginAdminImage(username: String): String?{
        return dao.getLoginAdminImage(username)
    }

    suspend fun getHappenEvent(startDate: String): Event?{
        return dao.getHappenEvent(startDate)
    }

    suspend fun getUpcomingEvent(startDate: String): Event?{
        return dao.getUpcomingEvent(startDate)
    }
}