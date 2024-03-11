package com.example.myopenweather.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myopenweather.OpenWeatherApplication
import com.example.myopenweather.data.OpenWeatherRepository
import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeather
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
sealed interface OpenWeatherUiState {
    data class Success(val openWeather: OpenWeather) : OpenWeatherUiState
    object Error : OpenWeatherUiState
    object Loading : OpenWeatherUiState
}

class OpenWeatherViewModel(private val openWeatherRepository: OpenWeatherRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var openWeatherUiState: OpenWeatherUiState by mutableStateOf(OpenWeatherUiState.Loading)
        private set
    var geoLocationUiState: GeoLocationUiState by mutableStateOf(GeoLocationUiState.Loading)
        private set
    var geoLocationByCoordsUiState: GeoLocationByCoordsUiState by mutableStateOf(GeoLocationByCoordsUiState.Loading)
        private set

// Go to https://openweathermap.org/api , create an account and get an API Key
    private val apiKey = "get one from openweather"
    /**
     * Call getGeoLocation() on init so we can display status immediately.
     */
    init {
        val cityName = "New York"
        getGeoLocation (cityName)
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
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
    fun getOpenWeather( latitude : String, longitude:String) {
        viewModelScope.launch {
            openWeatherUiState = OpenWeatherUiState.Loading
            openWeatherUiState = try {
                OpenWeatherUiState.Success(openWeatherRepository.getOpenWeather(latitude,longitude,apiKey))
            } catch (e: IOException) {
                OpenWeatherUiState.Error
            } catch (e: HttpException) {
                OpenWeatherUiState.Error
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