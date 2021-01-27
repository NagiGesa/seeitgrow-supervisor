package com.seeitgrow.supervisor.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import com.seeitgrow.supervisor.DataBase.Repository.RejectedRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RejectedViewModel @ViewModelInject constructor(
    private val repository: RejectedRepo
) : ViewModel() {

    val readAllRejectedMessage: LiveData<List<RejectedMessageDetail>>? = null


    fun readAllRejectedMessage(): LiveData<List<RejectedMessageDetail>>? =
        repository.readAllRejectedMessage()

    fun addRejectedMessage(user: List<RejectedMessageDetail>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRejectedMessage(user)
        }
    }

    fun DeleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletRejected()
        }
    }
}