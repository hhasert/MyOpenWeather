package com.example.myopenweather.data

import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeather
import com.example.myopenweather.network.OpenWeatherApiService

/**
 * Repository that fetch mars photos list from marsApi.
 */
interface OpenWeatherRepository {
    /** Fetches Weather data */
    suspend fun getOpenWeather(
        latitude: String,
        longitude: String,
        apiKey: String ): OpenWeather
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
    override suspend fun getOpenWeather(
        latitude:String,
        longitude:String,
        apiKey: String
    ): OpenWeather = openWeatherApiService.getOpenWeather( latitude, longitude,apiKey )
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
