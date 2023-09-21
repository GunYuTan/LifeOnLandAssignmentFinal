//package com.example.lifeonlandassignment
//
//import androidx.room.Room
//import androidx.test.platform.app.InstrumentationRegistry
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.lifeonlandassignment.database.AssignmentDatabase
//import com.example.lifeonlandassignment.database.AssignmentDatabaseDao
//import com.example.lifeonlandassignment.database.User
//import org.junit.After
//
//import org.junit.Test
//import org.junit.runner.RunWith
//
//import org.junit.Assert.*
//import org.junit.Before
//import java.io.IOException
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * See [testing documentation](http://d.android.com/tools/testing).
// */
//@RunWith(AndroidJUnit4::class)
//class AssignmentDatabaseTest{
//    private lateinit var assignmentDao: AssignmentDatabaseDao
//    private lateinit var db: AssignmentDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        // Using an in-memory database because the information stored here disappears when the
//        // process is killed.
//        db = Room.inMemoryDatabaseBuilder(context, AssignmentDatabase::class.java)
//                // Allowing main thread queries, just for testing.
//                .allowMainThreadQueries()
//                .build()
//        assignmentDao = db.assignmentDatabaseDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetUser() {
//        val insertUser = User(1,"123","123","123","123")
//        assignmentDao.insert(insertUser)
//        val tonight = assignmentDao.getAllUser()
//    }
//}
////class ExampleInstrumentedTest {
////    @Test
////    fun useAppContext() {
////        // Context of the app under test.
////        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
////        assertEquals("com.example.lifeonlandassignment", appContext.packageName)
////    }
////}