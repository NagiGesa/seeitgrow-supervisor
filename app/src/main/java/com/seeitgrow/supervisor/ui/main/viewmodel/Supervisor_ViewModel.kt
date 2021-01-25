package com.seeitgrow.supervisor.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails
import com.seeitgrow.supervisor.DataBase.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Supervisor_ViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val readAllData = repository.readAllData

    val getSupervisorCount = repository.getCount

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