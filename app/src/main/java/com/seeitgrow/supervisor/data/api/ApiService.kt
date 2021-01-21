package com.seeitgrow.supervisor.data.api

import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import com.seeitgrow.supervisor.Model.SiteListResponse
import retrofit2.http.GET
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


    @GET("CABIGetChampionListBySupervisorId")
    suspend fun getChampionLIst(
        @Query("SupervisorId") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<FarmerDetails>


    @GET("CABIGetFarmerDetailsByGroupFarmerId")
    suspend fun getSubFarmerList(
        @Query("GroupFarmerId") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<FarmerDetails>


    @GET("CABIGetPendingSiteListByFarmerId")
    suspend fun getPendingSite(
        @Query("FarmerId") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<SiteListResponse>


    @GET("CABIGetAllRejectionMessage")
    suspend fun getRejectedMessage(
        @Query("SeasonCode") seasonCode: String
    ): List<RejectedMessageDetail>

}