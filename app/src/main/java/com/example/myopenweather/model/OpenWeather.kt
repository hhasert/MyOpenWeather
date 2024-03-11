package com.example.myopenweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class OpenWeatherCurrent(
   @SerialName(value = "coord")
    val coordinates : Coordinates,
   @SerialName(value = "wind")
   val wind  : Wind,
   @SerialName(value = "name")
    val locationName : String,
    val weather : List <Weather>,
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

@Serializable
data class Coordinates(
    @SerialName(value = "lon")
    val longitude : Double,
    @SerialName(value = "lat")
    val latitude : Double
)
@Serializable
data class Wind(
    val speed: Double,
    val deg: Integer,
    val gust: Double? = null
)
@Serializable
data class Weather (
    val id : Integer,
    val main : String,
    val description : String,
    val icon : String
)