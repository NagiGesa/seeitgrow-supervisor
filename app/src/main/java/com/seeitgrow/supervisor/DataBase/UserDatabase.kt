package com.seeitgrow.supervisor.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seeitgrow.supervisor.DataBase.Dao.FarmerDao
import com.seeitgrow.supervisor.DataBase.Dao.Supervisor_Dao
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Model.SupervisorDetails

@Database(entities = [SupervisorDetails::class,FarmerDetails::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun superVisorDao(): Supervisor_Dao

    abstract fun farmerDao(): FarmerDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}