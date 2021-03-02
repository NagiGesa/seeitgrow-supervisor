package com.seeitgrow.supervisor.utils

import android.content.Context
import android.widget.Toast

object AppUtils {

    val SEASON_CODE = "LR2021"
    val CHAMPION_ID = "champion_Id"
    val FARMER_ID = "farmer_Id"
    val FARMER_NAME = "farmer_Name"

//    var IMAGEPATH = "http://40.123.251.25/Pictures/Kenya/"
    fun ImagePath(seasonCode: String, type: String): String {
        return if (type.equals("Site")) {
            "http://40.123.251.25/Pictures/Kenya/2021/KN/" + seasonCode + "/CABI-Sites//"
        } else {
            "http://40.123.251.25/Pictures/Kenya/2021/KN/" + seasonCode + "/CABI-Images/"
        }

    }

//    fun ImagePath(seasonCode: String, type: String): String {
//        return if (type.equals("Site")) {
//            "http://104.211.221.227/gesasandbox/Pictures/Kenya/2020/KN/" + seasonCode + "/CABI-Sites//"
//        } else {
//            "http://104.211.221.227/gesasandbox/Pictures/Kenya/2020/KN/" + seasonCode + "/CABI-Images/"
//        }
//
//    }

    fun showMessage(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}