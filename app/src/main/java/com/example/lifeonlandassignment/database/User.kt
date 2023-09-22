package com.example.lifeonlandassignment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int?,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "phoneNo")
    val phoneNo: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "userImage")
    val userImage: String?
)

@Entity(tableName = "admin_table")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val adminId: Int?,

    @ColumnInfo(name = "username")
    val adminUsername: String,

    @ColumnInfo(name = "password")
    val adminPassword: String,

    @ColumnInfo(name = "phoneNo")
    val adminPhoneNo: String,

    @ColumnInfo(name = "email")
    val adminEmail: String,

    @ColumnInfo(name = "userImage")
    val adminUserImage: String?
)

@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val eventId: Int?,

    @ColumnInfo(name = "adminId")
    val adminId: Int,

    @ColumnInfo(name = "eventName")
    val eventName: String,

    @ColumnInfo(name = "eventDescription")
    val eventDescription: String,

    @ColumnInfo(name = "eventDonation")
    val eventDonation: String
)

@Entity(tableName = "joined_event_table")
data class JoinedEvent(
    @PrimaryKey(autoGenerate = true)
    val joinedEventId: Int?,

    @ColumnInfo(name = "joinedEventUserId")
    val joinedEventUserId: Int,

    @ColumnInfo(name = "joinedEventEventId")
    val joinedEventEventId: Int
)

@Entity(tableName = "donation_table")
data class Donation(
    @PrimaryKey(autoGenerate = true)
    val donateId: Int?,

    @ColumnInfo(name = "donateUserId")
    val donateUserId: Int,

    @ColumnInfo(name = "donateEventId")
    val donateEventId: Int,

    @ColumnInfo(name = "donateAmount")
    val donateAmount: Double,

    @ColumnInfo(name = "donateMethod")
    val donateMethod: String
)
