package com.seeitgrow.supervisor.DataBase.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val phoneNumber: String,
    val name: String,
    val status: String,
    val modifiedOn: String
)