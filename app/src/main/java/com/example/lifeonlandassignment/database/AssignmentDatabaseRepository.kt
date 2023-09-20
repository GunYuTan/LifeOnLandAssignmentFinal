package com.example.lifeonlandassignment.database

class AssignmentDatabaseRepository (private val dao: AssignmentDatabaseDao){
    val userList = dao.getAllUser()

    suspend fun insert(user: User){
        return dao.insert(user)

    }

    suspend fun getUsername(username: String):User?{
        return dao.getUsername(username)
    }
}