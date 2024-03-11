package com.example.myopenweather.data

data class LocationData (
    val id: String,
    val name: String,
    val latitude: String,
    val longitude: String
)

object locationsData {
   val locationDataList = listOf(
        LocationData (
                id = "1",
                name = "Burggolf Westerpark",
                latitude = "52.055347",
                longitude = "4.432401"
        )
     )
}