package com.example.myopenweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myopenweather.data.LocationData
import com.example.myopenweather.data.LocationsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all items in the Room database.
 */
class LocationViewModel(locationsRepository: LocationsRepository): ViewModel() {
    val locationsUiState: StateFlow<LocationsUiState> =
        locationsRepository.getAllLocationsStream().map { LocationsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = LocationsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class LocationsUiState(val locationList: List<LocationData> = listOf())
