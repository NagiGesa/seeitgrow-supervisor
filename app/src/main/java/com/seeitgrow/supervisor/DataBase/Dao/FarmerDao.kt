package com.seeitgrow.supervisor.DataBase.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails

@Dao
interface FarmerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFarmerDetails(farmerDetails: List<FarmerDetails>)

    @Query("SELECT * from farmer_table where  IsGroupFarmer = '1' ")
     fun getAllFarmerDetailsByFarmerId() : LiveData<List<FarmerDetails>>

    @Query("SELECT * from farmer_table where GroupFarmerId = :groupId")
    fun getAllSubFarmerId(groupId :String?) : LiveData<List<FarmerDetails>>
}