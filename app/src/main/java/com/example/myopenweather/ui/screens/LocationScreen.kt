package com.example.myopenweather.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myopenweather.R
import com.example.myopenweather.model.GeoLocation


@Composable
fun LocationScreen(
    geoLocationByCoordsUiState: GeoLocationByCoordsUiState,
    retryAction: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) {
    when (geoLocationByCoordsUiState) {
        is GeoLocationByCoordsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GeoLocationByCoordsUiState.Success -> LocationsScreen(
            geoLocationByCoordsUiState.geolocations,
            onNextButtonClicked,
            contentPadding = contentPadding,
            modifier = modifier.fillMaxWidth()
        )
        is GeoLocationByCoordsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
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
            Text ("Location retrieved from\n reverse GeoLocation API")
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text (geolocations[0].name)
            Row(horizontalArrangement = Arrangement.Center,
                modifier = modifier)
            {
               Text ( fontSize = 15.sp, text = "Latitude " + lat + "   ")
               Text ( fontSize = 15.sp, text = "Longiitude " + lon)
            }
            Text (enName)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
            Button(
                modifier = Modifier. fillMaxWidth() .padding(dimensionResource(R.dimen.padding_medium)),
                // the button is enabled when the user makes a selection
                onClick = onNextButtonClicked
            ){
                Text(stringResource(R.string.next))
            }

        }
    }
