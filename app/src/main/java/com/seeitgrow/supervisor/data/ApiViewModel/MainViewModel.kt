package com.seeitgrow.supervisor.data.ApiViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.seeitgrow.supervisor.data.repository.ApiRepository
import com.seeitgrow.supervisor.utils.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val apiRepository: ApiRepository) :
    ViewModel() {


    fun getFarmerList(supervisorId: String, seasonCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getFarmerList(supervisorId, seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getChampionList(supervisorId: String, seasonCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getChampionList(supervisorId, seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getSubFarmerList(championId: String, seasonCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getSubFarmerList(championId, seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getSupervisorDetails(mobileNumber: String, seasonCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = apiRepository.getSupervisorDetails(
                        mobileNumber,
                        seasonCode
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getPendingSiteList(farmerId: String, seasonCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getPendingSiteList(farmerId, seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getPendingSiteImage(siteId: String, seasonCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getPendingSiteImage(siteId, seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getRejectedStatus(seasonCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRepository.getRejectedMessage(seasonCode)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun updateInitialSatus(
        SiteId: String,
        isApproved: String,
        isApproverId: String,
        approveComment: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = apiRepository.updateInitialSatus(
                        SiteId,
                        isApproved,
                        isApproverId,
                        approveComment
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun updateRepeatPictureStatus(
        SiteId: String,
        Status: String,
        uniqueId: String,
        isApproverId: String,
        approveComment: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = apiRepository.updateRepeatPictureStatus(
                        SiteId,
                        Status,
                        uniqueId,
                        isApproverId,
                        approveComment
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}