package com.example.myopenweather

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.myopenweather.data.AppContainer
import com.example.myopenweather.data.DefaultAppContainer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

// FusedLocationProviderClient - Main class for receiving location updates.
private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { currentLocation: Location? ->
                // Got last known location. In some rare situations this can be null.
            }

    }
}
