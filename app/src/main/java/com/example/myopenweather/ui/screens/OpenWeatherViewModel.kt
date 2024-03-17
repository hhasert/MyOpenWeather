package com.example.myopenweather.ui.screens

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.myopenweather.data.LocationData
import com.example.myopenweather.data.OpenWeatherRepository
import com.example.myopenweather.fusedLocationProviderClient
import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeatherCurrent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "MyOpenWeather"
/**
 * UI state for the Home screen
 */
sealed interface GeoLocationUiState {
    data class Success(val geolocations : List <GeoLocation>) : GeoLocationUiState
    data object Error : GeoLocationUiState
    data object Loading : GeoLocationUiState
}
sealed interface GeoLocationByCoordsUiState {
    data class Success(val geolocations : List <GeoLocation>) : GeoLocationByCoordsUiState
    data object Error : GeoLocationByCoordsUiState
    data object Loading : GeoLocationByCoordsUiState
}
sealed interface OpenWeatherCurrentUiState {
    data class Success(val openWeatherCurrent: OpenWeatherCurrent) : OpenWeatherCurrentUiState
    data object Error : OpenWeatherCurrentUiState
    data object Loading : OpenWeatherCurrentUiState
}

data class LocationUiState (
    var currentLocation: LocationData
)
class OpenWeatherViewModel(private val openWeatherRepository: OpenWeatherRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var openWeatherCurrentUiState: OpenWeatherCurrentUiState by mutableStateOf(OpenWeatherCurrentUiState.Loading)
        private set
    var geoLocationUiState: GeoLocationUiState by mutableStateOf(GeoLocationUiState.Loading)
        private set
    var geoLocationByCoordsUiState: GeoLocationByCoordsUiState by mutableStateOf(GeoLocationByCoordsUiState.Loading)
        private set

    private val _uiState = MutableStateFlow(LocationUiState(currentLocation = setCurrentLocation()))
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    // Go to https://openweathermap.org/api , create an account and get an API Key
    private val apiKey = BuildConfig.API_KEY
    /**
     * Call getGeoLocation() on init so we can display status immediately.
     */

    init {
        getGeoLocation(uiState.value.currentLocation.name)
        getCurrentLocation(
            { onGetCurrentLocationSuccess(it) },
            { onGetLastLocationFailed(it) }
        )
    }

    fun getGeoLocation( name : String) {
        viewModelScope.launch {
            geoLocationUiState = GeoLocationUiState.Loading
            geoLocationUiState = try {
                GeoLocationUiState.Success(openWeatherRepository.getGeoLocation(
                    name,
                    apiKey
                    )
                )
            } catch (e: IOException) {
                GeoLocationUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "ERROR: getGeoLocation - HttpException : " + e.message())
                GeoLocationUiState.Error
            }
        }
    }
    fun getGeoLocationByCoords( latitude : String, longitude:String) {
        viewModelScope.launch {
            geoLocationByCoordsUiState = GeoLocationByCoordsUiState.Loading
            geoLocationByCoordsUiState = try {
                GeoLocationByCoordsUiState.Success(openWeatherRepository.getGeoLocationByCoords(
                    latitude,
                    longitude,
                    apiKey
                    )
                )
            } catch (e: IOException) {
                GeoLocationByCoordsUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "ERROR: getGeoLocationByCoords -  HttpException : " + e.message())
                GeoLocationByCoordsUiState.Error
            }
        }
    }
    fun getOpenWeatherCurrent( latitude : String, longitude:String, units : String, language : String) {
        viewModelScope.launch {
            openWeatherCurrentUiState = OpenWeatherCurrentUiState.Loading
            Log.i(TAG, "getOpenWeather called")
            openWeatherCurrentUiState = try {
                OpenWeatherCurrentUiState.Success(openWeatherRepository.getOpenWeatherCurrent(
                    latitude,
                    longitude,
                    units,
                    language,
                    apiKey
                    )
                )
            } catch (e: IOException) {
                OpenWeatherCurrentUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "ERROR: getOpenWeatherCurrent - HttpException : " + e.message())
                OpenWeatherCurrentUiState.Error
            }
        }
    }
    //TODO : Get current location data from device
    private fun setCurrentLocation() : LocationData {
            val location = LocationData()
            location.id = "current"
            location.name = "Den Haag"
//            location.latitude = "52.069526"
//            location.longitude = "4.406018"
            return (location)
    }
    private fun onGetCurrentLocationSuccess (location: Pair<Double, Double>)
    {
        Log.d(TAG, "onGetCurrentLocation called")
        Log.d(TAG, "latitude : " + location.first)
        Log.d(TAG, "longitude : " + location.second)
        uiState.value.currentLocation.latitude = location.first.toString()
        uiState.value.currentLocation.longitude = location.second.toString()

    }
    private fun  onGetLastLocationFailed(e : Exception)
    {
        Log.d(TAG, "Exception getting location" + e.message)
    }
    /**
     * Retrieves the current user location asynchronously.
     *
     * @param onGetCurrentLocationSuccess Callback function invoked when the current location is successfully retrieved.
     *        It provides a Pair representing latitude and longitude.
     * @param onGetCurrentLocationFailed Callback function invoked when an error occurs while retrieving the current location.
     *        It provides the Exception that occurred.
     * @param priority Indicates the desired accuracy of the location retrieval. Default is high accuracy.
     *        If set to false, it uses balanced power accuracy.
     */
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(
        onGetLastLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetLastLocationFailed: (Exception) -> Unit
    ) {
            // Retrieve the last known location
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        // If location is not null, invoke the success callback with latitude and longitude
                        onGetLastLocationSuccess(Pair(it.latitude, it.longitude))
                    }
                }
                .addOnFailureListener { exception ->
                    // If an error occurs, invoke the failure callback with the exception
                    onGetLastLocationFailed(exception)
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
