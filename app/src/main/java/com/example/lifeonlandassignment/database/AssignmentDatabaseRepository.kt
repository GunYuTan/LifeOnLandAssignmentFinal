package com.example.lifeonlandassignment.database

import android.util.Log
import com.example.lifeonlandassignment.Global

class AssignmentDatabaseRepository (private val dao: AssignmentDatabaseDao){
    suspend fun getAllEvent(): List<Event>{
        return dao.getAllEvent()
    }

    suspend fun getAllUser(): List<User>{
        return dao.getAllUser()
    }

    suspend fun getAllDonation(): List<Donation>{
        return dao.getAllDonation()
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
    suspend fun insert(donation: Donation){
        return dao.insert(donation)
    }

    suspend fun clearEvent(){
        return dao.clearEvent()
    }

    suspend fun deleteEvent(deleteEvent: Int){
        return dao.deleteEvent(deleteEvent)
    }

    suspend fun update(user: User){
        return dao.update(user)
    }

    suspend fun update(admin: Admin){
        return dao.update(admin)
    }

    suspend fun updateEventDonation(eventId: Int, donationAmount: Double){
        return dao.updateEventDonation(eventId, donationAmount)
    }

    suspend fun getUsername(username: String):User?{
        return dao.getUsername(username)
    }

    suspend fun getAdminUsername(username: String):Admin?{
        return dao.getAdminUsername(username)
    }

    suspend fun getEventName(event: String): Event?{
        return dao.getEventName(event)
    }

    suspend fun getEventId(event: Int): Event?{
        return dao.getEventId(event)
    }

    suspend fun updateProfilePic(username: String, userImage: String){
        return dao.updateProfilePic(username, userImage)
    }

    suspend fun updateAdminProfilePic(username: String, userImage: String){
        return dao.updateAdminProfilePic(username, userImage)
    }
    suspend fun updateEvent(eventName: String, eventDescription: String, eventStartDate: String, eventEndDate: String, eventImage: String, eventId: Int){
        return dao.updateEvent(eventName, eventDescription, eventStartDate, eventEndDate, eventImage, eventId)
    }
    suspend fun updateEventNoImage(eventName: String, eventDescription: String, eventStartDate: String, eventEndDate: String, eventId: Int){
        return dao.updateEventNoImage(eventName, eventDescription, eventStartDate, eventEndDate, eventId)
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