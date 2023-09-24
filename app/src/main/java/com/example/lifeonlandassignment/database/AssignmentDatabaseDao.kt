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
    @Update
    suspend fun update(user: User)
    @Update
    suspend fun update(admin:Admin)
    @Query("SELECT * from user_table WHERE userId = :key")
    fun get(key:Long): User?

    @Query("DELETE FROM user_table")
    fun clear()

    @Query("SELECT * from user_table")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE username LIKE :username AND password LIKE :password")
    fun readAllData(username: String, password: String): LiveData<User>

    @Query("SELECT * FROM user_table WHERE username LIKE :username")
    suspend fun getUsername(username: String): User?
    @Query("SELECT * FROM admin_table WHERE username LIKE :username")
    suspend fun getAdminUsername(username: String): Admin?

    @Query("UPDATE user_table SET userImage = :userImage WHERE username = :username")
    suspend fun updateProfilePic(username: String, userImage: String)

    @Query("UPDATE admin_table SET userImage = :userImage WHERE username = :username")
    suspend fun updateAdminProfilePic(username: String, userImage: String)

    @Query("SELECT userImage FROM user_table WHERE username = :username")
    suspend fun getLoginUserImage(username: String): String?

    @Query("SELECT userImage FROM admin_table WHERE username = :username")
    suspend fun getLoginAdminImage(username: String): String?

    @Query("SELECT * FROM event_table WHERE DATE(STRFTIME('%Y-%m-%d', :startDate)) BETWEEN DATE(STRFTIME('%Y-%m-%d', eventStartDate)) AND DATE(STRFTIME('%Y-%m-%d', eventEndDate)) LIMIT 1")
    suspend fun getHappenEvent(startDate: String): Event?
}