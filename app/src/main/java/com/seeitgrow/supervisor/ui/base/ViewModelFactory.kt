package com.seeitgrow.supervisor.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.repository.ApiRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(ApiRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

