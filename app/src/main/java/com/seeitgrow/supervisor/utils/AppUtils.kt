package com.seeitgrow.supervisor.utils

import android.content.Context
import android.widget.Toast

object AppUtils {

    val SEASON_CODE = "SR2020"

    fun showMessage(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}