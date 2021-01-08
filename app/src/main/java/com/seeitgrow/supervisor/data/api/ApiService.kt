package com.seeitgrow.supervisor.data.api

import com.seeitgrow.supervisor.DataBase.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("GetCustomerDetailsByPhoneNumber.php")
    suspend fun getUsers(
        @Query("phoneNumber") number: String
    ): User

}