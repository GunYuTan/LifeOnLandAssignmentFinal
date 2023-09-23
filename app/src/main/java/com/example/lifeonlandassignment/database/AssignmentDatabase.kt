package com.example.lifeonlandassignment.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Admin::class, Event::class, JoinedEvent::class, Donation:: class], version = 1, exportSchema = false)
abstract class AssignmentDatabase : RoomDatabase() {
    abstract  val assignmentDatabaseDao: AssignmentDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: AssignmentDatabase? = null

        fun getInstance(context: Context): AssignmentDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AssignmentDatabase::class.java,
                        "AssignmentDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}