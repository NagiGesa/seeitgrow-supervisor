package com.seeitgrow.supervisor.DataBase.Repository

import androidx.lifecycle.LiveData
import com.seeitgrow.supervisor.DataBase.Dao.Supervisor_Dao
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails

class UserRepository(private val supervisor_Dao: Supervisor_Dao) {


    val readAllData: LiveData<List<SupervisorDetails>> = supervisor_Dao.readAllData()

    val getCount: LiveData<Integer> = supervisor_Dao.getSupervisorCount()

    suspend fun adduser(Supervisor_Dao: List<SupervisorDetails>) {
        supervisor_Dao.add(Supervisor_Dao)
    }

    suspend fun updateUser(Supervisor_Dao: SupervisorDetails) {
        supervisor_Dao.update(Supervisor_Dao)
    }

    suspend fun delete(Supervisor_Dao: SupervisorDetails) {
        supervisor_Dao.delete(Supervisor_Dao)
    }

    suspend fun deleteAll() {
        supervisor_Dao.deleteUser()
    }
}