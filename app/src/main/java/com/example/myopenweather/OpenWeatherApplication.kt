package com.example.myopenweather

import android.app.Application
import com.example.myopenweather.data.AppContainer
import com.example.myopenweather.data.DefaultAppContainer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

// FusedLocationProviderClient - Main class for receiving location updates.
lateinit var fusedLocationProviderClient: FusedLocationProviderClient

// LocationRequest - Requirements for the location updates, i.e.,
// how often you should receive updates, the priority, etc.
//private lateinit var locationRequest: LocationRequest

// LocationCallback - Called when FusedLocationProviderClient
// has a new Location
//private lateinit var locationCallback: LocationCallback

// This will store current location info
//private var currentLocation: Location? = null

class OpenWeatherApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }
}
