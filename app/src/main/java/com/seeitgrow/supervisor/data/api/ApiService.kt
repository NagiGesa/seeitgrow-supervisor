package com.seeitgrow.supervisor.data.api

import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("GetFarmerDetailsByGroupFarmerId")
    suspend fun getUsers(
        @Query("GroupFarmerId") number: String,
        @Query("SeasonCode") seasonCode: String
    ): List<FarmerDetails>

}