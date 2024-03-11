package com.example.myopenweather.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myopenweather.BuildConfig
import com.example.myopenweather.OpenWeatherApplication
import com.example.myopenweather.data.OpenWeatherRepository
import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeatherCurrent
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface GeoLocationUiState {
    data class Success(val geolocations : List <GeoLocation>) : GeoLocationUiState
    object Error : GeoLocationUiState
    object Loading : GeoLocationUiState
}
sealed interface GeoLocationByCoordsUiState {
    data class Success(val geolocations : List <GeoLocation>) : GeoLocationByCoordsUiState
    object Error : GeoLocationByCoordsUiState
    object Loading : GeoLocationByCoordsUiState
}
sealed interface OpenWeatherCurrentUiState {
    data class Success(val openWeatherCurrent: OpenWeatherCurrent) : OpenWeatherCurrentUiState
    object Error : OpenWeatherCurrentUiState
    object Loading : OpenWeatherCurrentUiState
}

class OpenWeatherViewModel(private val openWeatherRepository: OpenWeatherRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var openWeatherCurrentUiState: OpenWeatherCurrentUiState by mutableStateOf(OpenWeatherCurrentUiState.Loading)
        private set
    var geoLocationUiState: GeoLocationUiState by mutableStateOf(GeoLocationUiState.Loading)
        private set
    var geoLocationByCoordsUiState: GeoLocationByCoordsUiState by mutableStateOf(GeoLocationByCoordsUiState.Loading)
        private set

// Go to https://openweathermap.org/api , create an account and get an API Key
    private val apiKey = BuildConfig.API_KEY
    /**
     * Call getGeoLocation() on init so we can display status immediately.
     */
    init {
        val cityName = "New York"
        getGeoLocation (cityName)
    }

    fun getGeoLocation( name : String) {
        viewModelScope.launch {
            geoLocationUiState = GeoLocationUiState.Loading
            geoLocationUiState = try {
                GeoLocationUiState.Success(openWeatherRepository.getGeoLocation(name, apiKey))
            } catch (e: IOException) {
                GeoLocationUiState.Error
            } catch (e: HttpException) {
                GeoLocationUiState.Error
            }
        }
    }
    fun getGeoLocationByCoords( latitude : String, longitude:String) {
        viewModelScope.launch {
            geoLocationByCoordsUiState = GeoLocationByCoordsUiState.Loading
            geoLocationByCoordsUiState = try {
                GeoLocationByCoordsUiState.Success(openWeatherRepository.getGeoLocationByCoords(latitude, longitude, apiKey))
            } catch (e: IOException) {
                GeoLocationByCoordsUiState.Error
            } catch (e: HttpException) {
                GeoLocationByCoordsUiState.Error
            }
        }
    }
    fun getOpenWeatherCurrent( latitude : String, longitude:String) {
        viewModelScope.launch {
            openWeatherCurrentUiState = OpenWeatherCurrentUiState.Loading
            openWeatherCurrentUiState = try {
                OpenWeatherCurrentUiState.Success(openWeatherRepository.getOpenWeatherCurrent(latitude,longitude,apiKey))
            } catch (e: IOException) {
                OpenWeatherCurrentUiState.Error
            } catch (e: HttpException) {
                OpenWeatherCurrentUiState.Error
            }
        }
    }

    /**
     * Factory for [OpenWeatherViewModel] that takes [OpenWeatherRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OpenWeatherApplication)
                val openWeatherRepository = application.container.openWeatherRepository
                OpenWeatherViewModel(openWeatherRepository = openWeatherRepository)
            }
        }
    }
}