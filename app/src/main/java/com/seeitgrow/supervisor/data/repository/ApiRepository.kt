package com.seeitgrow.supervisor.data.repository

import com.seeitgrow.supervisor.data.api.ApiHelper

class ApiRepository(private val apiHelper: ApiHelper) {

    suspend fun getFarmerList(supervisorId : String, seasonCode: String) = apiHelper.getFarmerList(supervisorId,seasonCode)

    suspend fun getChampionList(supervisorId : String, seasonCode: String) = apiHelper.getChampionList(supervisorId,seasonCode)

    suspend fun getSubFarmerList(championId : String, seasonCode: String) = apiHelper.getSubFarmerList(championId,seasonCode)

    suspend fun getSupervisorDetails(mobileNumber : String, seasonCode: String) = apiHelper.getSupervisorDetails(mobileNumber,seasonCode)

    suspend fun getPendingSiteList(farmerId : String, seasonCode: String) = apiHelper.getPendingSiteList(farmerId,seasonCode)

    suspend fun getPendingSiteImage(siteId : String, seasonCode: String) = apiHelper.getPendingSiteImage(siteId,seasonCode)

    suspend fun getRejectedMessage( seasonCode: String) = apiHelper.getRejectedMessage(seasonCode)

    suspend fun updateInitialSatus(SiteId: String,isApproved: String, isApproverId :String,approveComment : String ) = apiHelper.updateInitialSatus(SiteId,isApproved,isApproverId,approveComment)

    suspend fun updateRepeatPictureStatus(SiteId: String,Status: String,uniqueId:String, isApproverId :String,approveComment : String ) = apiHelper.updateRepeatPictureStatus(SiteId,Status,uniqueId,isApproverId,approveComment)
}