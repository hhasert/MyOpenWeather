package com.example.myopenweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myopenweather.model.OpenWeatherCurrent
import com.example.myopenweather.ui.common.WeatherIcon
import com.example.myopenweather.ui.common.WindIcon
import com.example.myopenweather.ui.common.epochConvertToTime

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WeatherInfo (
    openWeatherCurrent: OpenWeatherCurrent,
    modifier: Modifier
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        {
            Text(
                style = MaterialTheme.typography.displayMedium,
                text = openWeatherCurrent.locationName
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp, end = 2.dp)
        ) {
            Text(
                style = MaterialTheme.typography.displayLarge,
                text = openWeatherCurrent.weather.temperature.toString() + " \u2103"
            )
            WeatherIcon(
                openWeatherCurrent.weatherCondition[0].icon,
                modifier = Modifier.size(80.dp, 80.dp)
            )
            WeatherClouds(openWeatherCurrent, modifier = Modifier)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 26.dp)
        ) {
            WindSpeedandDirection(openWeatherCurrent.wind.speed, openWeatherCurrent.wind.direction)
            Text(
                textAlign = TextAlign.End,
                text = epochConvertToTime(openWeatherCurrent.datetime + openWeatherCurrent.timezone).toString()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
@Composable
fun WeatherClouds(
    openWeatherCurrent: OpenWeatherCurrent,
    modifier: Modifier)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,)
    {
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
@Composable
fun WindSpeedandDirection ( speed: Double, direction : Int )
{
    Row () {
        Text(textAlign = TextAlign.Start, text = "Wind: " + speed + " m/s  ")
        WindIcon(direction.toFloat(), Modifier.size(14.dp))
    }
}