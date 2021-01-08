package com.seeitgrow.supervisor.data.repository

import com.seeitgrow.supervisor.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers(num : String) = apiHelper.getUsers(num)
}