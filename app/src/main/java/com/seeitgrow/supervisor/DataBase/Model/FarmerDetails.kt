package com.seeitgrow.supervisor.DataBase.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "farmer_table")
data class FarmerDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var WeatherStation: String? = null,
    val IsCABIFarmer: String? = null,
    val IsGroupFarmer: String? = null,
    val ModifiedOn: String? = null,
    val SubCountyOthers: String? = null,
    val SeasonDetails: String? = null,
    val CountyId: String? = null,
    val Gender: String? = null,
    val GroupFarmerName: String? = null,
    val Farmer_photographPath: String? = null,
    val Crop: String? = null,
    val TotalHectares: String? = null,
    val InsuredCrop: String? = null,
    val Education: String? = null,
    val DOB: String? = null,
    val ContactNumber: String? = null,
    val VillageOthers: String? = null,
    val EducationId: String? = null,
    val CountyOthers: String? = null,
    val VillageName: String? = null,
    val Ward: String? = null,
    val SeasonCode: String? = null,
    val Country: String? = null,
    val CreatedDateTime: String? = null,
    val RegistrationDocumentId: String? = null,
    val PromoCode: String? = null,


    val IsActive: String? =
        null,


    val ModifiedDateTime: String? =
        null,


    val ChampionUniqueCode: String? =
        null,


    val County: String? =
        null,


    val SubCountyId: String? =
        null,


    val Language: String? =
        null,


    val WardId: String? =
        null,


    val Ethinicity: String? =
        null,


    val MainPhoneNumber: String? =
        null,


    val FarmerID: String? =
        null,


    val CreatedOn: String? =
        null,


    val FarmerUniqueCode: String? =
        null,


    val ApkVersion: String? =
        null,


    val Age: String? =
        null,


    val Password: String? =
        null,


    val ResultStatus: String? =
        null,


    val WardOthers: String? =
        null,


    val FirstName: String? =
        null,


    val Error: String? =
        null,


    val SubCounty: String? =
        null,


    val IMEI: String? =
        null,


    val CropsAdvisoryEnroll: String? =
        null,


    val VillageID: String? =
        null,


    val Username: String? =
        null,


    val GroupFarmerId: String? =
        null,


    val FarmingExperience: String? =
        null,


    val DueDayRange: String? =
        null,


    val LanguageId: String? =
        null,


    val FatherName: String? =
        null,


    val RegistrationDocImagePath: String?? =
        null,
)
