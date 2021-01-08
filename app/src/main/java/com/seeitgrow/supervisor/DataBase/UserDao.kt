package com.seeitgrow.supervisor.DataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.seeitgrow.supervisor.DataBase.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteUser()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

}