package com.seeitgrow.supervisor.DataBase.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rejected_table")
data class RejectedMessageDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val NativeDescription: String? = null,

    val EnglishDescription: String? = null,

    val SeasonCode: String? = null,

    val Error: String? = null,

    val EnglishTitle: String? = null,

    val NativeTitle: String? = null,

    val MessageId: String? = null,
)