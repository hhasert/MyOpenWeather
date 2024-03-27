package com.example.myopenweather.ui.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myopenweather.R
import com.example.myopenweather.ui.screens.LocationPermissionScreen
import com.example.myopenweather.ui.screens.LocationScreen
import com.example.myopenweather.ui.screens.WeatherScreen
import com.example.myopenweather.ui.viewmodel.OpenWeatherViewModel
enum class MyOpenWeatherScreen(@StringRes val title: Int) {
    RequestPermissions(title = R.string.requestpermissions),
    Location(title = R.string.app_name),
    AddLocation(title = R.string.AddLocation),
    Weather(title = R.string.weather),
}
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun OpenWeatherNavHost(
    navController: NavHostController,
    openWeatherViewModel : OpenWeatherViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by openWeatherViewModel.uiState.collectAsState()

    val startDestination = if (areLocationPermissionsGranted()) {
        MyOpenWeatherScreen.Weather.name
    }
    else MyOpenWeatherScreen.RequestPermissions.name
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxHeight()
    ) {
        composable(route = MyOpenWeatherScreen.RequestPermissions.name) {
            LocationPermissionScreen { openWeatherViewModel.initCurrentLocation()
                navController.navigate(MyOpenWeatherScreen.Weather.name) }
        }

        composable(route = MyOpenWeatherScreen.Location.name) {
            LocationScreen(
                geoLocationByCoordsUiState = openWeatherViewModel.geoLocationByCoordsUiState,
                uiState.locations,
                retryAction = { /*TODO Should call the viewmodel to get location again*/},
                onNextButtonClicked =  {
                    uiState.currentLocation.latitude = it.latitude
                    uiState.currentLocation.longitude = it.longitude
                    openWeatherViewModel.getOpenWeatherCurrent(
                        latitude = uiState.currentLocation.latitude,
                        longitude = uiState.currentLocation.longitude,
                        units = "metric",
                        language = "en")
                    openWeatherViewModel.getOpenWeatherForecast(
                        latitude = uiState.currentLocation.latitude,
                        longitude = uiState.currentLocation.longitude,
                        units = "metric",
                        language = "en")
                    navController.navigate(MyOpenWeatherScreen.Weather.name)
                },
                onAddButtonClicked = {/*TODO add naviation to add location page*/},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
        composable(route = MyOpenWeatherScreen.Weather.name) {
            WeatherScreen(
                openWeatherCurrentUiState = openWeatherViewModel.openWeatherCurrentUiState,
                openWeatherForecastUiState = openWeatherViewModel.openWeatherForecastUiState,
                retryAction = { /*TODO*/ },
                onNextButtonClicked = { navController.navigate(MyOpenWeatherScreen.Weather.name) },
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Composable
private fun areLocationPermissionsGranted(): Boolean {
    val context = LocalContext.current
    return (ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
}