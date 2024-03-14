package com.example.myopenweather

import android.app.Application
import com.example.myopenweather.data.AppContainer
import com.example.myopenweather.data.DefaultAppContainer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class OpenWeatherApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }
}