package com.seeitgrow.supervisor.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seeitgrow.supervisor.DataBase.Dao.FarmerDao
import com.seeitgrow.supervisor.DataBase.Dao.Rejected_Dao
import com.seeitgrow.supervisor.DataBase.Dao.Supervisor_Dao
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails

@Database(
    entities = [SupervisorDetails::class, FarmerDetails::class, RejectedMessageDetail::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun superVisorDao(): Supervisor_Dao

    abstract fun farmerDao(): FarmerDao

    abstract fun rejectedDao(): Rejected_Dao

}