package com.example.lifeonlandassignment.database

import android.graphics.Bitmap
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
