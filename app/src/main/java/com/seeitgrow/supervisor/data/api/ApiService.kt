package com.seeitgrow.supervisor.data.api

import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails
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

}