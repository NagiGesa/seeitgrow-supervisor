package com.seeitgrow.supervisor.data.repository

import com.seeitgrow.supervisor.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers(num : String, seasonCode: String) = apiHelper.getUsers(num,seasonCode)
}