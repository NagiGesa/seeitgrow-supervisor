package com.seeitgrow.supervisor.data.repository

import com.seeitgrow.supervisor.data.api.ApiHelper

class ApiRepository(private val apiHelper: ApiHelper) {

    suspend fun getFarmerList(supervisorId : String, seasonCode: String) = apiHelper.getFarmerList(supervisorId,seasonCode)


    suspend fun getSupervisorDetails(mobileNumber : String, seasonCode: String) = apiHelper.getSupervisorDetails(mobileNumber,seasonCode)
}