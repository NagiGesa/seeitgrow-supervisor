package com.seeitgrow.supervisor.DataBase.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails

@Dao
interface Supervisor_Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(user: List<SupervisorDetails>)

    @Update
    suspend fun update(user: SupervisorDetails)

    @Delete
    suspend fun delete(user: SupervisorDetails)

    @Query("DELETE FROM supervisor_details")
    suspend fun deleteUser()

    @Query("SELECT * FROM supervisor_details")
    fun readAllData(): LiveData<List<SupervisorDetails>>

    @Query("SELECT Count(*) from supervisor_details")
    fun getSupervisorCount(): LiveData<Int>

}