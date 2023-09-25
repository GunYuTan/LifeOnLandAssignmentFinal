package com.example.lifeonlandassignment.manage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import java.lang.IllegalArgumentException

class ClientDataViewModelFactory   (private  val repository: AssignmentDatabaseRepository,
                                    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClientDataViewModel::class.java)) {
            return ClientDataViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}