package com.seeitgrow.supervisor.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Repository.FarmerRepo
import com.seeitgrow.supervisor.DataBase.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FarmerViewModel(application: Application) : AndroidViewModel(application) {

    val readAllFarmerByGroupId: LiveData<List<FarmerDetails>>? = null

    private val repository: FarmerRepo

    init {
        val farmerDao = UserDatabase.getDatabase(application).farmerDao()
        repository = FarmerRepo(farmerDao)


    }

     fun readAllFarmerByGroupId(groupId: String): LiveData<List<FarmerDetails>>? =
        repository.readAllFarmerByGroupId(groupId)


    fun addUser(user: List<FarmerDetails>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFarmer(user)
        }
    }
}