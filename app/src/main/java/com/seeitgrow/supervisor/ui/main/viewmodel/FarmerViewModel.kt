package com.seeitgrow.supervisor.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Repository.FarmerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FarmerViewModel @ViewModelInject constructor(
    private val repository: FarmerRepo
) :
    ViewModel() {

    val readAllFarmerByGroupId: LiveData<List<FarmerDetails>>? = null

    val readAllSubFarmerByChampoionId: LiveData<List<FarmerDetails>>? = null

    fun readAllFarmerByGroupId(): LiveData<List<FarmerDetails>>? =
        repository.readAllFarmerByGroupId()

    fun readAllSubFarmerByGroupId(championId: String): LiveData<List<FarmerDetails>>? =
        repository.readAllSubFarmerByGroupId(championId)


    fun addUser(user: List<FarmerDetails>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFarmer(user)
        }
    }

    fun DeleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFarmer()
        }
    }
}