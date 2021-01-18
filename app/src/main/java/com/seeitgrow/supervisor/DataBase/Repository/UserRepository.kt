package com.seeitgrow.supervisor.DataBase.Repository

import androidx.lifecycle.LiveData
import com.seeitgrow.supervisor.DataBase.Dao.UserDao
import com.seeitgrow.supervisor.DataBase.Model.User

class UserRepository(private val userDao: UserDao) {


    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun adduser(user: User) {
        userDao.add(user)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun delete(user: User){
        userDao.delete(user)
    }

    suspend fun deleteAll(){
        userDao.deleteUser()
    }
}