package com.seeitgrow.supervisor.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers(num: String, seasonCode: String) = apiService.getUsers(num, seasonCode)
}