package com.example.myopenweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.myopenweather.ui.common.WeatherIcon
import com.example.myopenweather.ui.common.WindIcon
import com.example.myopenweather.ui.common.epochConvertToDate
import com.example.myopenweather.ui.common.epochConvertToTime
import java.time.LocalDate
import kotlin.math.round
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WeatherForecast (openWeatherForecast: OpenWeatherForecast, modifier: Modifier)
{
    Card(
          shape = MaterialTheme.shapes.medium,
          elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.height (250.dp)
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

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ForecastItem(index : Int, openWeatherForecast: List<ForecastData>, timezone : Long)
{
    Card (
          shape = MaterialTheme.shapes.medium,
          modifier = Modifier.fillMaxWidth()
                    .width(68.dp)
    ){
        Spacer(modifier = Modifier.height(8.dp))
        DateTime(openWeatherForecast[index].datetime, timezone)
        WeatherIcon(
            openWeatherForecast[index].weatherCondition[0].icon,
            modifier = Modifier.size(64.dp, 64.dp)
        )
        Text(
            textAlign = TextAlign.Center,
            text = "" + round(openWeatherForecast[index].weather.temperature * 10) / 10 + " \u2103",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        RainOrSnow(
            openWeatherForecast[index].precipitationProbability,
            openWeatherForecast[index].rain?.threeHour ?: 0.0,
            openWeatherForecast[index].snow?.threeHour ?: 0.0
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
            text = "wind",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            textAlign = TextAlign.Center,
            text = "" + round(openWeatherForecast[index].wind.speed * 10) / 10 + " m/s",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(4.dp)
        )
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        )
        {
            WindIcon(
                openWeatherForecast[index].wind.direction.toFloat(),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DateTime (epoch : Long, timezone: Long )
{
    val date: LocalDate = epochConvertToDate(epoch + timezone)

    Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.labelSmall,
        text = date.dayOfMonth.toString() + "-" + date.monthValue,
        modifier = Modifier.fillMaxWidth())
    Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.labelSmall,
        text = epochConvertToTime(epoch + timezone).toString(),
        modifier = Modifier.fillMaxWidth())
}
@Composable
fun RainOrSnow ( probability: Double, rain: Double, snow : Double)
{
    Text(textAlign = TextAlign.Center, style = MaterialTheme.typography.labelSmall, text ="precp", modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(4.dp))
    Text(textAlign = TextAlign.Center, text = "" + (probability * 100).roundToInt() + " %",
        modifier = Modifier.fillMaxWidth()
    )
    if (rain != 0.0) {
        Text(textAlign = TextAlign.Center, text = "" + (round(rain * 10) / 10) + " mm", modifier = Modifier.fillMaxWidth())
    }
    if (snow != 0.0) {

        Text(textAlign = TextAlign.Center, text = "" + (round(snow * 10) / 10) + " mm", modifier = Modifier.fillMaxWidth())
    }
    if (rain == 0.0 && snow == 0.0 )
        Spacer(modifier = Modifier.height(16.dp))
}
