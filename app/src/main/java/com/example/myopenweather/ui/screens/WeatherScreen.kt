package com.example.myopenweather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myopenweather.R
import com.example.myopenweather.model.OpenWeatherCurrent
import java.time.Instant
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    openWeatherCurrentUiState: OpenWeatherCurrentUiState,
    retryAction: () -> Unit,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) {
    when (openWeatherCurrentUiState) {
        is OpenWeatherCurrentUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is OpenWeatherCurrentUiState.Success -> CurrentWeatherScreen(
            openWeatherCurrentUiState.openWeatherCurrent, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
        )
        is OpenWeatherCurrentUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentWeatherScreen(
    openWeatherCurrent: OpenWeatherCurrent,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherInfo (openWeatherCurrent , modifier = modifier.height(128.dp))
            Spacer(modifier = Modifier.height(16.dp))

            Text("Location : " + openWeatherCurrent.locationName)
            Text("latitude : " + openWeatherCurrent.coordinates.latitude)
            Text("longitude : " + openWeatherCurrent.coordinates.longitude)
            Text("Wind Speed : " + openWeatherCurrent.wind.speed + " m/s")
            Text("Wind Direction : " + openWeatherCurrent.wind.direction + " deg")
            Text("Weather : " + openWeatherCurrent.weatherCondition[0].summary)
            Text("Temperature : " + openWeatherCurrent.weather.temperature + " Celcius")
            Text("Cloudiness : " + openWeatherCurrent.clouds.cloudiness + " %")
            Text(
                "Time : " + DateTimeFormatter.ISO_INSTANT
                    .format(Instant.ofEpochSecond(openWeatherCurrent.datetime))
            )
        }
    }
}
@Composable
fun WeatherInfo (
    openWeatherCurrent: OpenWeatherCurrent,
    modifier: Modifier = Modifier
)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(16.dp) )
        {
            WeatherIcon (openWeatherCurrent)
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text( fontSize = 14.sp,
                      text = openWeatherCurrent.weatherCondition[0].description)
                Spacer (modifier = Modifier.height(4.dp))
                Text( fontSize = 14.sp,
                      text = openWeatherCurrent.clouds.cloudiness.toString() + " % clouds")
            }
        }
    }
}
@Composable
fun WeatherIcon( openWeatherCurrent: OpenWeatherCurrent,
                 modifier: Modifier = Modifier)
{
    Surface(
        modifier = modifier.size(64.dp, 64.dp )
    )
    {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(
                "https://openweathermap.org/img/w/" + openWeatherCurrent.weatherCondition[0].icon + ".png"
            )
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.weathericon),
            contentScale = ContentScale.Crop,
        )
    }
}
