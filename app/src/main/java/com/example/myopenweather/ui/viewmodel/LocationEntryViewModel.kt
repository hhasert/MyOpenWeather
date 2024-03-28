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
    var locationUiState by mutableStateOf(LocationUiState())
        private set

    /**
     * Updates the [locationUiState] with the value provided in the argument.
     */

    suspend fun saveItem() {
            locationsRepository.insertLocation(locationUiState.location)
    }
}

/**
 * Represents Ui State for an Item.
 */
data class LocationUiState(
    val location : LocationData = LocationData()
)