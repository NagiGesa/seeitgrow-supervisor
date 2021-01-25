package com.seeitgrow.supervisor.DataBase.Repository

import androidx.lifecycle.LiveData
import com.seeitgrow.supervisor.DataBase.Dao.Rejected_Dao
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import javax.inject.Inject

class RejectedRepo @Inject constructor(private val rejectedDao: Rejected_Dao) {

    var rejectedMessageDetail: LiveData<List<RejectedMessageDetail>>? = null

    fun readAllRejectedMessage(): LiveData<List<RejectedMessageDetail>>? {
        rejectedMessageDetail = rejectedDao.getAllRejectedMessage()
        return rejectedMessageDetail
    }

    suspend fun addRejectedMessage(messageDetail: List<RejectedMessageDetail>) {
        rejectedDao.insertRejectedMessage(messageDetail)
    }

    suspend fun deleteMesage() {
        rejectedDao.deleteRejected()
    }
}