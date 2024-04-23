package com.example.myopenweather.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myopenweather.data.LocationData
import com.example.myopenweather.data.LocationsRepository

/**
 * ViewModel to validate and insert items in the Room database.
 */
class LocationEntryViewModel(private val locationsRepository: LocationsRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var locationNameUiState by mutableStateOf(LocationNameUiState())
        private set
    var locationListUiState by mutableStateOf(LocationListUiState())
        private set
    /**
     * Updates the [locationUiState] with the value provided in the argument.
     */
    fun updateUiState(location: LocationName) {
        locationNameUiState =
            LocationNameUiState(location = location)
    }
    suspend fun saveItem() {
            //locationsRepository.insertLocation(locationListUiState)
    }
}

/**
 * Represents Ui State for an Item.
 */
data class LocationNameUiState(
    var location : LocationName = LocationName()
)

data class LocationName (
    var name : String = ""
)
data class LocationListUiState(
    var locationList : List <LocationData> = emptyList()
)