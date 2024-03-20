package com.example.myopenweather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myopenweather.model.ForecastData
import com.example.myopenweather.model.OpenWeatherForecast
import java.time.LocalDate
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ForecastItem(index : Int, openWeatherForecast: List<ForecastData>, timezone : Int)
{
    val date : LocalDate = epochConvertToDate(openWeatherForecast[index].datetime + timezone)
    Card (
        modifier = Modifier
            .size(width = 68.dp, height = 240.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.labelSmall,
            text = date.dayOfMonth.toString() + "-" + date.monthValue,
            modifier = Modifier.fillMaxWidth())
        Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.labelSmall,
            text = epochConvertToTime(openWeatherForecast[index].datetime).toString(),
            modifier = Modifier.fillMaxWidth())
        WeatherIcon(openWeatherForecast[index].weatherCondition[0].icon, modifier = Modifier.size(64.dp, 64.dp) )
        Text(textAlign = TextAlign.Center,
            text = openWeatherForecast[index].weather.temperature.roundToInt().toString() + " \u2103",
            modifier = Modifier.fillMaxWidth() )
        Spacer(modifier = Modifier.height(6.dp))
        Text(textAlign = TextAlign.Center, style = MaterialTheme.typography.labelSmall, text ="rain", modifier = Modifier.fillMaxWidth())
        Text(textAlign = TextAlign.Center, text = "" + (openWeatherForecast[index].precipitationProbability * 100).roundToInt() + " %",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(textAlign = TextAlign.Center,  style = MaterialTheme.typography.labelSmall,text ="wind", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(2.dp))
        Text(textAlign = TextAlign.Center, text = "" + (openWeatherForecast[index].wind.speed).roundToInt() + " m/s",
            modifier = Modifier.fillMaxWidth() )
        Spacer(modifier = Modifier.height(4.dp)
        )
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        )
        {
            WindIcon(openWeatherForecast[index].wind.direction.toFloat(), modifier= Modifier.size(14.dp))
        }
    }
}
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WeatherForecast (openWeatherForecast: OpenWeatherForecast, modifier: Modifier)
{
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        LazyRow(
            modifier = modifier.padding(horizontal = 4.dp)
                .fillMaxWidth()
        )
        {
            items(openWeatherForecast.count)
            {
                ForecastItem(it, openWeatherForecast.forecast, openWeatherForecast.city.timezone)
            }
        }
    }
}

