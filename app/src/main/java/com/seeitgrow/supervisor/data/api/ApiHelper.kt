package com.seeitgrow.supervisor.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getFarmerList(superVisorId: String, seasonCode: String) = apiService.getFarmerList(superVisorId, seasonCode)

    suspend fun getSupervisorDetails(mobileNumber: String, seasonCode: String) = apiService.getSupervisorDetails(mobileNumber, seasonCode)

    suspend fun getChampionList(superVisorId: String, seasonCode: String) = apiService.getChampionLIst(superVisorId, seasonCode)

    suspend fun getSubFarmerList(championId: String, seasonCode: String) = apiService.getSubFarmerList(championId, seasonCode)

    suspend fun getPendingSiteList(farmerId: String, seasonCode: String) = apiService.getPendingSite(farmerId, seasonCode)

    suspend fun getPendingSiteImage(siteId: String, seasonCode: String) = apiService.getPendingSiteImage(siteId, seasonCode)

    suspend fun getRejectedMessage(seasonCode: String) = apiService.getRejectedMessage(seasonCode)

    suspend fun updateInitialSatus(SiteId: String,isApproved: String, isApproverId :String,approveComment : String ) = apiService.updateInitialPictureStatus(SiteId,isApproved,isApproverId,approveComment)

    suspend fun updateRepeatPictureStatus(SiteId: String,Status: String,uniqueId:String, isApproverId :String,approveComment : String ) = apiService.updateRepeatPictureStatus(SiteId,Status,uniqueId,isApproverId,approveComment)

}