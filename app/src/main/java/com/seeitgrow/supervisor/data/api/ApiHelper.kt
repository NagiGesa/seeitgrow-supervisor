package com.seeitgrow.supervisor.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getFarmerList(superVisorId: String, seasonCode: String) = apiService.getFarmerList(superVisorId, seasonCode)

    suspend fun getSupervisorDetails(mobileNumber: String, seasonCode: String) = apiService.getSupervisorDetails(mobileNumber, seasonCode)
}