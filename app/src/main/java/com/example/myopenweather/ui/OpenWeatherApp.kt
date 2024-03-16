package com.example.myopenweather.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myopenweather.R
import com.example.myopenweather.ui.screens.LocationPermissionScreen
import com.example.myopenweather.ui.screens.LocationScreen
import com.example.myopenweather.ui.screens.OpenWeatherViewModel
import com.example.myopenweather.ui.screens.WeatherScreen

//private const val TAG = "MyOpenWeather"
/**
 * enum values that represent the screens in the app
 */
enum class MyOpenWeatherScreen(@StringRes val title: Int) {
    Permissions(title = R.string.requestpermissions),
    Location(title = R.string.app_name),
    Weather(title = R.string.weather),
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenWeatherTopAppBar(
    currentScreen: MyOpenWeatherScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenWeatherApp(context : Context,
                   navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MyOpenWeatherScreen.valueOf(
        backStackEntry?.destination?.route ?: MyOpenWeatherScreen.Location.name
    )
    val openWeatherViewModel: OpenWeatherViewModel =
        viewModel(factory = OpenWeatherViewModel.Factory)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { OpenWeatherTopAppBar(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            scrollBehavior = scrollBehavior) }
    ) { innerPadding ->
        val uiState by openWeatherViewModel.uiState.collectAsState()
        val startDestination : String
        if (ActivityCompat.checkSelfPermission(
               context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            startDestination = MyOpenWeatherScreen.Permissions.name
        }
        else startDestination = MyOpenWeatherScreen.Location.name
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            composable(route = MyOpenWeatherScreen.Permissions.name) {
                LocationPermissionScreen(onNextButtonClicked = { navController.navigate(MyOpenWeatherScreen.Location.name) })
            }
            composable(route = MyOpenWeatherScreen.Location.name) {
                LocationScreen(
                    geoLocationUiState = openWeatherViewModel.geoLocationUiState,
                    retryAction = { /*TODO*/},
                    onNextButtonClicked = {
                                            openWeatherViewModel.getOpenWeatherCurrent(
                                            latitude = uiState.currentLocation.latitude,
                                            longitude = uiState.currentLocation.longitude,
                                            units = "metric",
                                            language = "en")
                                            navController.navigate(MyOpenWeatherScreen.Weather.name)
                                          },

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = MyOpenWeatherScreen.Weather.name) {
                WeatherScreen(
                    openWeatherCurrentUiState = openWeatherViewModel.openWeatherCurrentUiState,
                    retryAction = { /*TODO*/ },
                    onNextButtonClicked = { navController.navigate(MyOpenWeatherScreen.Weather.name) },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}
