package com.example.lifeonlandassignment.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AssignmentDatabaseDao {
    @Insert
    suspend fun insert(user: User)
    @Insert
    suspend fun insert(admin: Admin)
    @Insert
    suspend fun insert(event: Event)
    @Insert
    suspend fun insert(donation: Donation)
    @Insert
    suspend fun insert(joinedEvent: JoinedEvent)
    @Insert
    suspend fun insert(notification: Notification)
    @Update
    suspend fun update(user: User)
    @Update
    suspend fun update(admin:Admin)
    @Query("SELECT * from user_table WHERE userId = :key")
    fun get(key:Long): User?

    @Query("DELETE FROM user_table")
    fun clear()

    @Query("DELETE FROM event_table WHERE eventId = :deleteEvent")
    suspend fun deleteEvent(deleteEvent: Int)
    @Query("DELETE FROM joined_event_table WHERE joinedEventUserId = :userId AND joinedEventEventId = :eventId")
    suspend fun deleteEvent(userId: Int, eventId: Int)

    @Query("DELETE FROM noti_table WHERE notiId = :deleteNoti")
    suspend fun deleteNoti(deleteNoti: Int)

    @Query("DELETE FROM event_table")
    suspend fun clearEvent()

    @Query("SELECT * from user_table")
    suspend fun getAllUser(): List<User>

    @Query("SELECT * from event_table ORDER BY eventId DESC")
    suspend fun getAllEvent(): List<Event>

    @Query("SELECT * from donation_table")
    suspend fun getAllDonation(): List<Donation>
    @Query("SELECT * from noti_table ORDER BY notiId DESC")
    suspend fun getAllNotification(): List<Notification>

    @Query("SELECT * FROM user_table WHERE username LIKE :username AND password LIKE :password")
    fun readAllData(username: String, password: String): LiveData<User>

    @Query("SELECT * FROM user_table WHERE username LIKE :username")
    suspend fun getUsername(username: String): User?
    @Query("SELECT * FROM admin_table WHERE username LIKE :username")
    suspend fun getAdminUsername(username: String): Admin?

    @Query("SELECT * FROM event_table WHERE eventName LIKE :eventName")
    suspend fun getEventName(eventName: String): Event?

    @Query("SELECT * FROM event_table WHERE eventId = :eventId")
    suspend fun getEventId(eventId: Int): Event?
    @Query("SELECT * FROM user_table WHERE userId = :userId")
    suspend fun getUserId(userId: Int): User?

    @Query("SELECT * FROM joined_event_table WHERE joinedEventUserId = :userId AND joinedEventEventId = :eventId")
    suspend fun getJoinEvent(userId: Int, eventId: Int): JoinedEvent?

    @Query("UPDATE user_table SET userImage = :userImage WHERE username = :username")
    suspend fun updateProfilePic(username: String, userImage: String)

    @Query("UPDATE admin_table SET userImage = :userImage WHERE username = :username")
    suspend fun updateAdminProfilePic(username: String, userImage: String)

    @Query("UPDATE event_table SET eventDonation = eventDonation + :donationAmount WHERE eventId = :eventId")
    suspend fun updateEventDonation(eventId: Int, donationAmount: Double)

    @Query("UPDATE event_table SET eventName = :eventName, eventDescription = :eventDescription, eventStartDate = :eventStartDate, eventEndDate = :eventEndDate, eventImage = :eventImage WHERE eventId = :eventId")
    suspend fun updateEvent(eventName: String, eventDescription: String, eventStartDate: String, eventEndDate: String, eventImage: String, eventId: Int)
    @Query("UPDATE event_table SET eventName = :eventName, eventDescription = :eventDescription, eventStartDate = :eventStartDate, eventEndDate = :eventEndDate WHERE eventId = :eventId")
    suspend fun updateEventNoImage(eventName: String, eventDescription: String, eventStartDate: String, eventEndDate: String, eventId: Int)

    @Query("SELECT userImage FROM user_table WHERE username = :username")
    suspend fun getLoginUserImage(username: String): String?

    @Query("SELECT userImage FROM admin_table WHERE username = :username")
    suspend fun getLoginAdminImage(username: String): String?

    @Query("SELECT * FROM event_table WHERE DATE(STRFTIME('%Y-%m-%d', :startDate)) BETWEEN DATE(STRFTIME('%Y-%m-%d', eventStartDate)) AND DATE(STRFTIME('%Y-%m-%d', eventEndDate)) LIMIT 1")
    suspend fun getHappenEvent(startDate: String): Event?

    @Query("SELECT * FROM event_table WHERE DATE(STRFTIME('%Y-%m-%d', eventStartDate)) > DATE(STRFTIME('%Y-%m-%d', :startDate)) ORDER BY DATE(STRFTIME('%s', eventStartDate)) ASC LIMIT 1")
    suspend fun getUpcomingEvent(startDate: String): Event?
}