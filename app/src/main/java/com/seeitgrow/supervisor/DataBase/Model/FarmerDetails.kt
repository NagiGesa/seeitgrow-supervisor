package com.seeitgrow.supervisor.DataBase.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "farmer_table")
data class FarmerDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var WeatherStation: String? = null,

    var IsCABIFarmer: String? = null,

    var IsGroupFarmer: String? = null,

    var ModifiedOn: String? = null,

    var SubCountyOthers: String? = null,

    var SeasonDetails: String? = null,

    var CountyId: String? = null,

    var Gender: String? = null,

    var GroupFarmerName: String? = null,

    var Farmer_photographPath: String? = null,

    var Crop: String? = null,

    var TotalHectares: String? = null,

    var InsuredCrop: String? = null,

    var Education: String? = null,

    var DOB: String? = null,

    var ContactNumber: String? = null,

    var VillageOthers: String? = null,

    var EducationId: String? = null,

    var CountyOthers: String? = null,

    var VillageName: String? = null,

    var Ward: String? = null,

    var SeasonCode: String? = null,

    var Country: String? = null,

    var CreatedDateTime: String? = null,

    var RegistrationDocumentId: String? = null,

    var PromoCode: String? = null,

    var IsActive: String? = null,

    var ModifiedDateTime: String? = null,

    var ChampionUniqueCode: String? = null,

    var TotalPendingImage: String? = null,

    var County: String? = null,

    var SubCountyId: String? = null,

    var Language: String? = null,

    var WardId: String? = null,

    var Ethinicity: String? = null,

    var MainPhoneNumber: String? = null,

    var FarmerID: String? = null,

    var CreatedOn: String? = null,

    var FarmerUniqueCode: String? = null,

    var ApkVersion: String? = null,

    var Age: String? = null,

    var Password: String? = null,

    var ResultStatus: String? = null,

    var WardOthers: String? = null,

    var FirstName: String? = null,

    var Error: String? = null,

    var SubCounty: String? = null,

    var IMEI: String? = null,

    var CropsAdvisoryEnroll: String? = null,

    var VillageID: String? = null,

    var Username: String? = null,

    var GroupFarmerId: String? = null,

    var FarmingExperience: String? = null,

    var DueDayRange: String? = null,

    var LanguageId: String? = null,

    var FatherName: String? = null,

    var RegistrationDocImagePath: String? = null,
)
