package com.example.myopenweather.data

import com.example.myopenweather.network.OpenWeatherCurrentApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val openWeatherRepository: OpenWeatherRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.openweathermap.org/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true;isLenient = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: OpenWeatherCurrentApiService by lazy {
        retrofit.create(OpenWeatherCurrentApiService::class.java)
    }

    /**
     * DI implementation for Mars photos repository
     */
    override val openWeatherRepository: OpenWeatherRepository by lazy {
        NetworkOpenWeatherRepository(retrofitService)
    }
}
