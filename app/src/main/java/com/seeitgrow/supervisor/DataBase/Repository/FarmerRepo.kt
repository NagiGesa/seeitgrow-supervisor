package com.seeitgrow.supervisor.DataBase.Repository

import androidx.lifecycle.LiveData
import com.seeitgrow.supervisor.DataBase.Dao.FarmerDao
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails

class FarmerRepo(private val farmerDao: FarmerDao) {

    var farmerDetails: LiveData<List<FarmerDetails>>?=null

     fun readAllFarmerByGroupId(groupId: String):  LiveData<List<FarmerDetails>>? {
        farmerDetails = farmerDao.getAllFarmerDetailsByFarmerId(groupId)
        return farmerDetails
    }

    suspend fun addFarmer(user: List<FarmerDetails>) {
        farmerDao.insertFarmerDetails(user)
    }

}