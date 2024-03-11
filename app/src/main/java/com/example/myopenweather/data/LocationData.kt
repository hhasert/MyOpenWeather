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
        ),
        LocationData (
                id = "2",
                name = "Golfbaan Bentwoud",
                latitude = "52.069423",
                longitude = "4.571822"
       ),
       LocationData (
                id = "3",
                name = "Golfbaan Spaarnwoude",
                latitude = "52.426639",
                longitude = "4.693563"
       )
   )
}
