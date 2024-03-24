package com.example.myopenweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myopenweather.R
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
        modifier = Modifier.height (420.dp)
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
                    .width(80.dp)
    ){
        Spacer(modifier = Modifier.height(8.dp))
        DateTime(openWeatherForecast[index].datetime, timezone)
                Text(
            textAlign = TextAlign.Center,
            text = "" + openWeatherForecast[index].weatherCondition[0].summary,
            modifier = Modifier.fillMaxWidth()
        )
        WeatherIcon(
            openWeatherForecast[index].weatherCondition[0].icon,
            modifier = Modifier.size(64.dp, 64.dp)
        )
        ForecastTemperature (openWeatherForecast[index].weather.temperature, openWeatherForecast[index].weather.temperatureFeelsLike)
        RainOrSnow(
            openWeatherForecast[index].precipitationProbability,
            openWeatherForecast[index].rain?.threeHour ?: 0.0,
            openWeatherForecast[index].snow?.threeHour ?: 0.0
        )
        Humidity(openWeatherForecast[index].weather.humidity)
        Wind(openWeatherForecast[index].wind.speed, openWeatherForecast[index].wind.direction)

    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DateTime (epoch : Long, timezone: Long )
{
    val date: LocalDate = epochConvertToDate(epoch + timezone)

    Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.labelMedium,
        text = date.dayOfWeek.toString().take(2),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth() )
    Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.bodySmall,
        text = date.dayOfMonth.toString() + "-" + date.monthValue,
        modifier = Modifier.fillMaxWidth())
    Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.bodySmall,
        text = epochConvertToTime(epoch + timezone).toString(),
        modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.inversePrimary)
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ForecastTemperature( temperature : Double, realFeel: Double)
{
    Text(
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.labelMedium,
    text = "" + round(temperature * 10) / 10 + " \u2103",
    modifier = Modifier.fillMaxWidth()
)
    Spacer(modifier = Modifier.height(2.dp))
    Row( horizontalArrangement = Arrangement.Center,  modifier = Modifier.fillMaxWidth() .padding(start = 2.dp) )
    {
        val backhand = if( isSystemInDarkTheme() ) R.drawable.back_hand_light else R.drawable.back_hand_dark
        Image(
            modifier = Modifier.size(12.dp),
            painter = painterResource(backhand),
            contentDescription = stringResource(R.string.loading)
        )
      Text(
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        text = " " + round(realFeel * 10) / 10 + " \u2103 ",
      )

}
    Spacer(modifier = Modifier.height(8.dp))
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.inversePrimary)
    Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(18.dp))
    Spacer(modifier = Modifier.height(8.dp))
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.inversePrimary)
    Spacer(modifier = Modifier.height(8.dp))
}
@Composable
fun Wind (speed: Double, direction: Int)
{
    Text(
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelSmall,
        text = "wind",
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        textAlign = TextAlign.Center,
        text = "" + round(speed * 10) / 10 + " m/s",
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
            direction.toFloat(),
            modifier = Modifier.size(14.dp)
        )
    }
}
@Composable
fun Humidity(humidity : Int)
{
    Text(
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelSmall,
        text = "humid",
        modifier = Modifier.fillMaxWidth()
    )
    Text(textAlign = TextAlign.Center, text = "$humidity %",
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.inversePrimary)
    Spacer(modifier = Modifier.height(8.dp))
}