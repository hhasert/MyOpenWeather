package com.example.myopenweather.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myopenweather.R
import com.example.myopenweather.model.GeoLocation

@Composable
fun LocationScreen(
    geoLocationUiState: GeoLocationUiState,
    retryAction: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) {
    when (geoLocationUiState) {
        is GeoLocationUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GeoLocationUiState.Success -> LocationsScreen(
            geoLocationUiState.geolocations,
            onNextButtonClicked,
            contentPadding = contentPadding,
            modifier = modifier.fillMaxWidth()
        )
        is GeoLocationUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}
    @Composable
    fun LocationsScreen(
        geolocations: List<GeoLocation>,
        onNextButtonClicked: () -> Unit,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(5.dp),
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val lat = geolocations[0].latitude.toString()
            val lon = geolocations[0].longitude.toString()
            val enName = geolocations[0].localNames.get("en").toString()
            Text (geolocations[0].name)
            Text ("Latitude  " + lat)
            Text ("Longiitude  " + lon)
            Text (enName)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Button(
                modifier = Modifier,
                // the button is enabled when the user makes a selection
                onClick = onNextButtonClicked
            ){
                Text(stringResource(R.string.next))
            }

        }
    }