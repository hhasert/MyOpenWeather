package com.example.myopenweather.data

import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeatherCurrent
import com.example.myopenweather.network.OpenWeatherApiService

/**
 * Repository that fetch location and weather info from OpenWeather.
 */
interface OpenWeatherRepository {
    /** Fetches Weather data */
    suspend fun getOpenWeatherCurrent(
        latitude: String,
        longitude: String,
        units : String,
        language : String,
        apiKey: String ): OpenWeatherCurrent
    suspend fun getGeoLocation(
        cityName: String,
        apiKey: String ): List <GeoLocation>
    suspend fun getGeoLocationByCoords(
        latitude: String,
        longitude:String,
        apiKey: String ): List <GeoLocation>
}

/**
 * Network Implementation of Repository that fetch weather.
 */
class NetworkOpenWeatherRepository(
    private val openWeatherApiService: OpenWeatherApiService
) : OpenWeatherRepository {
    /** Fetches Weather data from openWeatherApi*/
    override suspend fun getOpenWeatherCurrent(
        latitude:String,
        longitude:String,
        units : String,
        language: String,
        apiKey: String
    ): OpenWeatherCurrent = openWeatherApiService.getOpenWeatherCurrent( latitude, longitude, units, language, apiKey )
    override suspend fun getGeoLocation(
        cityName : String,
        apiKey : String
    ): List <GeoLocation> = openWeatherApiService.getGeoLocation( cityName ,apiKey )
    override suspend fun getGeoLocationByCoords(
        latitude : String,
        longitude : String,
        apiKey : String
    ): List <GeoLocation> = openWeatherApiService.getGeoLocationByCoords( latitude, longitude ,apiKey )
}
