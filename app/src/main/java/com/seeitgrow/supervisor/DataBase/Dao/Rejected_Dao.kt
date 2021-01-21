package com.seeitgrow.supervisor.DataBase.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail

@Dao
interface Rejected_Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRejectedMessage(farmerDetails: List<RejectedMessageDetail>)

    @Query("SELECT * from rejected_table")
    fun getAllRejectedMessage(): LiveData<List<RejectedMessageDetail>>

    @Query("Delete from rejected_table")
    suspend fun deleteRejected()

}