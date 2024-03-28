package com.example.myopenweather.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.myopenweather.R
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
    onAddButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (geoLocationByCoordsUiState) {
        is GeoLocationByCoordsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GeoLocationByCoordsUiState.Success -> LocationsScreen(
            locations,
            onNextButtonClicked,
            onAddButtonClicked,
            modifier = Modifier
        )
        is GeoLocationByCoordsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}
    @Composable
    fun LocationsScreen(
        locations : List <LocationData>,
        onNextButtonClicked: (location: LocationData) -> Unit,
        onAddButtonClicked: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            modifier = Modifier,
            floatingActionButton = {
            FloatingActionButton(
                onClick = onAddButtonClicked,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.AddLocation)
                )
               }
            }
        )
        {
            LocationsList(locations, onNextButtonClicked, modifier.padding(it))
            }
     }
