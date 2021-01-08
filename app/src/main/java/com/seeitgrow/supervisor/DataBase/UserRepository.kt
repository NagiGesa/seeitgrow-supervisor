package com.seeitgrow.supervisor.DataBase

import androidx.lifecycle.LiveData

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