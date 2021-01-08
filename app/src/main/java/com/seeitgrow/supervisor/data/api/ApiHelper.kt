package com.seeitgrow.supervisor.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers(num: String) = apiService.getUsers(num)
}