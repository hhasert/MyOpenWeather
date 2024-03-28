package com.example.myopenweather.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myopenweather.OpenWeatherApplication

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for LocationsViewModel
        initializer {
            LocationEntryViewModel(
                OpenWeatherApplication().container.locationsRepository
            )
        }
     }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [OpenWeatherApplication].
 */
fun CreationExtras.OpenWeatherApplication(): OpenWeatherApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OpenWeatherApplication)
