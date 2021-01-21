package com.seeitgrow.supervisor.utils

import android.content.Context
import android.widget.Toast

object AppUtils {

    val SEASON_CODE = "SR2020"
    val CHAMPION_ID = "champion_Id"
    val FARMER_ID = "farmer_Id"


    fun ImagePath(seasonCode: String, type: String): String {
        return if (type.equals("Site")) {
            "http://104.211.221.227/gesasandbox/Pictures/Kenya/2020/KN/" + seasonCode + "/Sites/"
        } else {
            "http://104.211.221.227/gesasandbox/Pictures/Kenya/2020/KN/" + seasonCode + "/Images/"
        }

    }

    fun showMessage(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}