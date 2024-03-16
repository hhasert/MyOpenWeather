package com.example.myopenweather

import android.app.Application
import com.example.myopenweather.data.AppContainer
import com.example.myopenweather.data.DefaultAppContainer

// FusedLocationProviderClient - Main class for receiving location updates.
//private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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
        container = DefaultAppContainer()
/*        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { currentLocation: Location? ->
                // Got last known location. In some rare situations this can be null.
            }*/

    }
}
