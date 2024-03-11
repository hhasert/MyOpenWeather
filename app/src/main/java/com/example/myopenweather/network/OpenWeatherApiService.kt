package com.example.myopenweather.network

import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("data/3.0/onecall")
    suspend fun getOpenWeather(
        @Query(value = "lat") latitude : String,
        @Query(value = "lon") longitude : String,
        @Query(value="appid") apiKey : String
    ): OpenWeather

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