package com.example.myopenweather.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myopenweather.data.LocationData
import com.example.myopenweather.ui.common.ErrorScreen
import com.example.myopenweather.ui.common.LoadingScreen
import com.example.myopenweather.ui.components.LocationsList
import com.example.myopenweather.ui.viewmodel.GeoLocationByCoordsUiState


@Composable
fun LocationScreen(
    geoLocationByCoordsUiState: GeoLocationByCoordsUiState,
    locations : List <LocationData>,
    retryAction: () -> Unit,
    onNextButtonClicked: (location : LocationData) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (geoLocationByCoordsUiState) {
        is GeoLocationByCoordsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GeoLocationByCoordsUiState.Success -> LocationsScreen(
            locations,
            onNextButtonClicked,
            modifier = Modifier
        )
        is GeoLocationByCoordsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}
    @Composable
    fun LocationsScreen(
        locations : List <LocationData>,
        onNextButtonClicked: (location: LocationData) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        LocationsList( locations , onNextButtonClicked, modifier,)
    }
