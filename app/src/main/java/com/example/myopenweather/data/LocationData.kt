package com.example.myopenweather.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationData (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = "",
    var latitude: String = "",
    var longitude: String = ""
)
val locationsData = mutableListOf(
        LocationData
        (
                name = "Burggolf Westerpark",
                latitude = "52.055347",
                longitude = "4.432401"
        ),
       LocationData(
                name = "Golfbaan Bentwoud",
                latitude = "52.069423",
                longitude = "4.571822"
       ),
       LocationData(
                name = "Golfbaan Spaarnwoude",
                latitude = "52.426639",
                longitude = "4.693563"
       ),
       LocationData(
                name = "Islantilla",
                latitude = "37.214665",
                longitude = "-7.229323"
    ),
       LocationData(
                name = "Faro Airport",
                latitude = "37.017842",
                longitude = "-7.972626"
    ),
)

