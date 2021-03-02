package com.seeitgrow.supervisor.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    //    private const val BASE_URL = "http://104.211.221.227/gesasandbox/IFPRIAPIKN/api/KNAPK/"
    private const val BASE_URL = "http://40.123.251.25/SeeitgrowAPIV2/api/KNAPK/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}