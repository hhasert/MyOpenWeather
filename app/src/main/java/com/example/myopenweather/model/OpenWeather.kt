package com.example.myopenweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import java.text.DecimalFormat

@Serializable
data class OpenWeatherCurrent(
    @SerialName(value = "lat")
    val latitude : Double,
    @SerialName(value = "lon")
    val longitude : Double,
    val timezone :  String,
    @SerialName(value = "timezone_offset")
    val timezoneOffset : String,
    // TODO : Check if current is properly serializable
    val current:JsonObject
)

@Serializable
data class GeoLocation(
    val name : String,
    // TODO : Check if there is a better way to serialize local_names, for now localNames.get("key").toString()
    //        will give the value example : localName = geolocations[0].localNames.get("en").toString()
   @SerialName(value = "local_names")
    val localNames: JsonObject,
    @SerialName(value = "lat")
    val latitude : Double,
    @SerialName(value = "lon")
    val longitude : Double,
    val country : String,
    val state : String
)
