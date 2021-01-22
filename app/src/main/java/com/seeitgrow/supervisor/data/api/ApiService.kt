package com.seeitgrow.supervisor.data.api

import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails
import com.seeitgrow.supervisor.Model.SiteListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("CABIGetFarmerDetailsBySupervisorId")
    suspend fun getFarmerList(
        @Query("SupervisorId") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<FarmerDetails>


    @GET("CABIAppLoginForSupervisor")
    suspend fun getSupervisorDetails(
        @Query("MobileNumber") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<SupervisorDetails>


    @GET("CABIGetPendingChampionSiteList")
    suspend fun getChampionLIst(
        @Query("SupervisorId") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<FarmerDetails>


    @GET("CABIGetPendingFarmerSiteListByGroupFarmerId")
    suspend fun getSubFarmerList(
        @Query("GroupFarmerId") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<FarmerDetails>


    @GET("CABIGetPendingSiteListByFarmerId")
    suspend fun getPendingSite(
        @Query("FarmerId") farmerId: String,
        @Query("SeasonCode") seasonCode: String
    ): List<SiteListResponse>


    @GET("CABIGetPendingImagesListBySiteId")
    suspend fun getPendingSiteImage(
        @Query("SiteId") siteId: String,
        @Query("SeasonCode") seasonCode: String
    ): List<SiteListResponse>


    @GET("CABIGetAllRejectionMessage")
    suspend fun getRejectedMessage(
        @Query("SeasonCode") seasonCode: String
    ): List<RejectedMessageDetail>

    @POST("CABIUpdateInitialPictureStatus")
    suspend fun updateInitialPictureStatus(
        @Query("SiteId") siteId: String,
        @Query("IsApproved") isApproved: String,
        @Query("ApproverId") approverId: String,
        @Query("ApproveComment") approvecmt: String
    ): String


    @POST("CABIUpdateRepeatPictureStatus")
    suspend fun updateRepeatPictureStatus(
        @Query("SiteId") siteId: String,
        @Query("Status") status: String,
        @Query("UniqueId") uniqueId: String,
        @Query("ApproverId") approverId: String,
        @Query("ApproveComment") approvecmt: String
    ): String

}