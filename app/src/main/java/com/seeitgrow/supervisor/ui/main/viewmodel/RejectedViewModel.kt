package com.seeitgrow.supervisor.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import com.seeitgrow.supervisor.DataBase.Repository.RejectedRepo
import com.seeitgrow.supervisor.DataBase.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RejectedViewModel(application: Application) : AndroidViewModel(application) {

    val readAllRejectedMessage: LiveData<List<RejectedMessageDetail>>? = null

    private val repository: RejectedRepo

    init {
        val rejectedRepo = UserDatabase.getDatabase(application).rejectedDao()
        repository = RejectedRepo(rejectedRepo)


    }

    fun readAllRejectedMessage(): LiveData<List<RejectedMessageDetail>>? =
        repository.readAllRejectedMessage()

    fun addRejectedMessage(user: List<RejectedMessageDetail>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRejectedMessage(user)
        }
    }
}