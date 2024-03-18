package com.example.myopenweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
// See https://openweathermap.org/current for the API definition
// Some parameters are optional but this is not properly documented

@Serializable
data class OpenWeatherCurrent(
   @SerialName(value = "coord")
    val coordinates : Coordinates,
   @SerialName(value = "wind")
   val wind  : Wind,
   @SerialName(value = "name")
    val locationName : String,
   @SerialName(value = "weather")
    val weatherCondition : List <WeatherCondition>,
   @SerialName(value = "main")
    val weather : Weather,
    val visibility : Int = -1,
    val rain : RainOrSnow? = null,
    val snow : RainOrSnow? = null,
    val clouds : Clouds,
    val timezone : Int,
   @SerialName(value = "dt")
    val datetime : Long,
)
@Serializable
data class OpenWeatherForecast(
    @SerialName(value = "cnt")
    val count: Int,
    @SerialName(value = "list")
    val forecast : List <ForecastData>,
    val city : City,
)


// See https://openweathermap.org/api/geocoding-api for teh API definition
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
    val state : String,
    @SerialName(value = "sys")
    val sun : Sun? = null,
)

// Supporting classes
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
    @SerialName(value = "deg")
    val direction: Int,
    val gust: Double? = null
)
@Serializable
data class WeatherCondition (
    val id : Int,
    @SerialName(value = "main")
    val summary : String,
    val description : String,
    val icon : String
)
@Serializable
data class Weather (
   @SerialName(value = "temp")
   val temperature: Double,
   @SerialName(value = "feels_like")
   val temperatureFeelsLike : Double,
   @SerialName(value = "temp_min")
   val temperatureMinimum : Double,
   @SerialName(value = "temp_max")
   val temperatureMaximum : Double,
   val pressure : Int,
   val humidity : Int,
   @SerialName(value = "sea_level")
   val pressureSeaLevel : Int? = null,
   @SerialName(value = "grnd_level")
   val  pressureGroundLevel : Int? = null
)
@Serializable
data class RainOrSnow (
    @SerialName(value = "1h")
    val OneHour : Double? = null,
    @SerialName(value = "3h")
    val ThreeHour: Double? = null
)
@Serializable
data class Clouds(
    @SerialName(value = "all")
    val cloudiness : Int? = null
)
@Serializable
data class Sun (
    val sunrise : Long,
    val sunset  : Long,
)
@Serializable
data class PartOfDay(
    @SerialName(value = "pod")
    val dayOrNight: String
)
@Serializable
data class City(
    val id: String,
    val name: String,
    @SerialName(value = "coord")
    val coordinates : Coordinates,
    val country : String,
    val population : String,
    val timezone : String,
    val sunrise : String,
    val sunset : String,
)
@Serializable
data class ForecastData (
    @SerialName(value = "dt")
    val datetime : Long,
    @SerialName(value = "main")
    val weather : Weather,
    @SerialName(value = "weather")
    val weatherCondition : List <WeatherCondition>,
    val clouds : Clouds,
    @SerialName(value = "wind")
    val wind  : Wind,
    val visibility : Int,
    @SerialName(value = "pop")
    val precipitationProbability : Double,
    val rain : RainOrSnow? = null,
    val snow : RainOrSnow? = null,
    @SerialName(value = "sys")
    val partOfDay : PartOfDay,
)