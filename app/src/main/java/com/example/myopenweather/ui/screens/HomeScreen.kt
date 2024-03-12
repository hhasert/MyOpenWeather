package com.example.myopenweather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myopenweather.R
import com.example.myopenweather.model.GeoLocation
import com.example.myopenweather.model.OpenWeatherCurrent

@Composable
fun HomeScreen(
    geoLocationUiState: GeoLocationUiState,
    openWeatherCurrentUiState: OpenWeatherCurrentUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
/*  when (geoLocationUiState) {
        is GeoLocationUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GeoLocationUiState.Success -> PhotosGridScreen2(
            geoLocationUiState.geolocations, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
        )
        is GeoLocationUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    } */
    when (openWeatherCurrentUiState) {
        is OpenWeatherCurrentUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is OpenWeatherCurrentUiState.Success -> PhotosGridScreen(
            openWeatherCurrentUiState.openWeatherCurrent, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
        )
        is OpenWeatherCurrentUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PhotosGridScreen(
    openWeatherCurrent: OpenWeatherCurrent,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text ("Location : " + openWeatherCurrent.locationName)
        Text ("latitude : " + openWeatherCurrent.coordinates.latitude)
        Text ("longitude : " + openWeatherCurrent.coordinates.longitude)
        Text ("Wind Speed : " + openWeatherCurrent.wind.speed + " m/s")
        Text ("Wind Direction : " + openWeatherCurrent.wind.direction + " deg")
        Text ("Weather : " + openWeatherCurrent.weatherCondition[0].summary)
        Text ("Weather : " + openWeatherCurrent.weatherCondition[0].description)
        Text ("Temperature : " + openWeatherCurrent.weather.temperature + " Celcius")
        Text ("Cloudiness : " + openWeatherCurrent.clouds.cloudiness + " %")
    }
}
@Composable
fun PhotosGridScreen2(
    geolocations: List<GeoLocation>,
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
    }
}
/**
 * The home screen displaying Weather inFo

@Composable
fun PhotosGridScreen(
    geolocations: List<GeoLocation>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = geolocations, key = { geolocation -> geolocation.name }) { geolocation ->
            MarsPhotoCard(
                photo,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun MarsPhotoCard(photo: MarsPhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo.imgSrc)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.mars_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    MarsPhotosTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    MarsPhotosTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    MarsPhotosTheme {
        val mockData = List(10) { MarsPhoto("$it", "") }
        PhotosGridScreen(mockData)
    }
}
*/