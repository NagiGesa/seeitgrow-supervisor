package com.seeitgrow.supervisor.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails
import com.seeitgrow.supervisor.DataBase.Repository.UserRepository
import com.seeitgrow.supervisor.DataBase.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Supervidor_ViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<SupervisorDetails>>

    val getSupervisorCount: LiveData<Integer>

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).superVisorDao()
        repository = UserRepository(userDao)

        readAllData = repository.readAllData

        getSupervisorCount = repository.getCount


    }

    fun addUser(SupervisorDetails: List<SupervisorDetails>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.adduser(SupervisorDetails)
        }
    }

    fun updateUser(SupervisorDetails: SupervisorDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(SupervisorDetails)
        }
    }

    fun deleteUser(SupervisorDetails: SupervisorDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(SupervisorDetails)
        }
    }

    fun DeleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}