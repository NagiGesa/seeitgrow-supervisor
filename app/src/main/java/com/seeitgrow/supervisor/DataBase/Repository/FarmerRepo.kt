package com.seeitgrow.supervisor.DataBase.Repository

import androidx.lifecycle.LiveData
import com.seeitgrow.supervisor.DataBase.Dao.FarmerDao
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails

class FarmerRepo(private val farmerDao: FarmerDao) {

    var farmerDetails: LiveData<List<FarmerDetails>>?=null

     fun readAllFarmerByGroupId():  LiveData<List<FarmerDetails>>? {
        farmerDetails = farmerDao.getAllFarmerDetailsByFarmerId()
        return farmerDetails
    }

    fun readAllSubFarmerByGroupId(championId : String):  LiveData<List<FarmerDetails>>? {
        farmerDetails = farmerDao.getAllSubFarmerId(championId)
        return farmerDetails
    }

    suspend fun addFarmer(user: List<FarmerDetails>) {
        farmerDao.insertFarmerDetails(user)
    }

}