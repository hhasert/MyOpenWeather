package com.example.myopenweather.network

import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeatherCurrent
import com.example.myopenweather.model.OpenWeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getOpenWeatherCurrent(
        @Query(value = "lat") latitude: String,
        @Query(value = "lon") longitude: String,
        @Query(value = "units") units: String,
        @Query(value = "lang" ) language : String,
        @Query(value = "appid") apiKey: String,
    ): OpenWeatherCurrent

    @GET("data/2.5/forecast?")
    suspend fun getOpenWeatherForecast(
        @Query(value = "lat") latitude: String,
        @Query(value = "lon") longitude: String,
        @Query(value = "units") units: String,
        @Query(value = "lang" ) language : String,
        @Query(value = "appid") apiKey: String
    ) : OpenWeatherForecast

    @GET("geo/1.0/direct")
    suspend fun getGeoLocation (
        @Query(value = "q") cityName : String,
        @Query(value="appid") apiKey : String
    ): List <GeoLocation>

    @GET("geo/1.0/reverse")
    suspend fun getGeoLocationByCoords (
        @Query(value = "lat") latitude : String,
        @Query(value = "lon") longitude : String,
        @Query(value="appid") apiKey : String
    ): List <GeoLocation>
}