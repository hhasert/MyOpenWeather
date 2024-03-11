package com.example.myopenweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import java.text.DecimalFormat

@Serializable
data class OpenWeather(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)

@Serializable
data class GeoLocation(
    val name : String,
   @SerialName(value = "local_names")
    val localNames: JsonObject,
    @SerialName(value = "lat")
    val latitude : Double,
    @SerialName(value = "lon")
    val longitude : Double,
    val country : String,
    val state : String
)
