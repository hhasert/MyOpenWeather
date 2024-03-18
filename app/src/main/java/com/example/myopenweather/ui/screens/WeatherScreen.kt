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
    modifier :Modifier =  Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) { Column ()
    {
    Card(
        modifier = modifier.fillMaxWidth()
            .align(Alignment.CenterHorizontally),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth())
        {
            Text(openWeatherCurrent.locationName)
        }
       Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth() .padding(start=12.dp) ) {
            Text( fontSize = 28.sp, text = openWeatherCurrent.weather.temperature.toString() + " C" )
            WeatherInfo(openWeatherCurrent, modifier = modifier.height(100.dp) )
       }
    }
    Spacer(modifier = Modifier.height(32.dp))
    Card (
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        )
        {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = modifier){

                  Text("latitude : " + openWeatherCurrent.coordinates.latitude + "     ")
                  Text("longitude : " + openWeatherCurrent.coordinates.longitude)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    Text("Weather : " + openWeatherCurrent.weatherCondition[0].summary)
                    Text("Wind Speed : " + openWeatherCurrent.wind.speed + " m/s")
                    Text("Wind Direction : " + openWeatherCurrent.wind.direction + " deg")

                    Text(
                        "Time : " + DateTimeFormatter.ISO_INSTANT
                            .format(Instant.ofEpochSecond(openWeatherCurrent.datetime))
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
   }
}
@Composable
fun WeatherInfo (
    openWeatherCurrent: OpenWeatherCurrent,
    modifier: Modifier)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Row(horizontalArrangement = Arrangement.Center,
            modifier = modifier)
        {
            WeatherIcon (openWeatherCurrent, modifier = Modifier
                            .padding(22.dp))


        }
        Row(horizontalArrangement = Arrangement.Center)
        {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                fontSize = 20.sp,
                text = openWeatherCurrent.weather.temperature.toString() + " C"
            )
        }
    }
}
@Composable
fun WeatherIcon( openWeatherCurrent: OpenWeatherCurrent,
                 modifier: Modifier = Modifier)
{ Row {

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(
                "https://openweathermap.org/img/w/" + openWeatherCurrent.weatherCondition[0].icon + ".png"
            )
                .crossfade(true).build(),

            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.weathericon),
            contentScale = ContentScale.FillBounds ,
            modifier = modifier.size(80.dp. 80.dp)
        )

        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                fontSize = 14.sp,
                text = openWeatherCurrent.weatherCondition[0].description
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                fontSize = 14.sp,
                text = openWeatherCurrent.clouds.cloudiness.toString() + " % clouds"
            )
        }
    }
}

