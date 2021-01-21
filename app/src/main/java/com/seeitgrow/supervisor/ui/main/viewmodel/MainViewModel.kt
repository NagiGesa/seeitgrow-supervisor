package com.seeitgrow.supervisor.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.seeitgrow.supervisor.data.repository.ApiRepository
import com.seeitgrow.supervisor.utils.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val apiRepository: ApiRepository) : ViewModel() {


    fun getFarmerList(supervisorId : String, seasonCode : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getFarmerList(supervisorId,seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getChampionList(supervisorId : String, seasonCode : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getChampionList(supervisorId,seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getSubFarmerList(championId : String, seasonCode : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getSubFarmerList(championId,seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getSupervisorDetails(mobileNumber : String, seasonCode : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getSupervisorDetails(mobileNumber,seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getPendingSiteList(farmerId : String, seasonCode : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getPendingSiteList(farmerId,seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getRejectedStatus(seasonCode : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getRejectedMessage(seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}