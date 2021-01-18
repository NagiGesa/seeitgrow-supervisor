package com.seeitgrow.supervisor.DataBase.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supervisor_details")
data class SupervisorDetails (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var CreatedOn: String? = null,
    val EmailId: String? = null,
    val Error : String? = null,
    val IsActive: String? = null,
    val MainPhoneNumber: String? = null,
    val ModifiedOn: String? = null,
    val Name: String? = null,
    val Password: String? = null,
    val SeasonCode: String? = null,
    val SupervisorId: String? = null,
    val Username: String? = null,

)