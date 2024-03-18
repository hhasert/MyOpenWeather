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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myopenweather.R
import com.example.myopenweather.model.ForecastData
import com.example.myopenweather.model.OpenWeatherCurrent
import com.example.myopenweather.model.OpenWeatherForecast
import java.time.LocalDate
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WeatherScreen(
    openWeatherCurrentUiState: OpenWeatherCurrentUiState,
    openWeatherForecastUiState : OpenWeatherForecastUiState,
    retryAction: () -> Unit,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) {
    when (openWeatherCurrentUiState) {
        is OpenWeatherCurrentUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is OpenWeatherCurrentUiState.Success -> {
            when (openWeatherForecastUiState) {
                is OpenWeatherForecastUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
                is OpenWeatherForecastUiState.Success -> CurrentWeatherScreen(
                    openWeatherCurrentUiState.openWeatherCurrent,
                    openWeatherForecastUiState.openWeatherForecast,
                    contentPadding = contentPadding,
                    modifier = modifier.fillMaxWidth()
                )
                is OpenWeatherForecastUiState.Error -> ErrorScreen(
                    retryAction,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
            is OpenWeatherCurrentUiState.Error -> ErrorScreen(
            retryAction,
            modifier = modifier.fillMaxSize()
            )
        }
    }

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CurrentWeatherScreen(
    openWeatherCurrent: OpenWeatherCurrent,
    openWeatherForecast: OpenWeatherForecast,
    modifier :Modifier =  Modifier,
    contentPadding: PaddingValues = PaddingValues(5.dp),
) {
    Column ()
    {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp))
        {
            Text(style = MaterialTheme.typography.displayMedium, text = openWeatherCurrent.locationName)
        }
       Row(horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp, end = 2.dp) ) {
            Text( style = MaterialTheme.typography.displayLarge, text = openWeatherCurrent.weather.temperature.toString() + " \u2103" )
            WeatherIcon(openWeatherCurrent.weatherCondition[0].icon, modifier = Modifier.size(80.dp, 80.dp))
            WeatherInfo(openWeatherCurrent, modifier = Modifier)
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
                WeatherDetails( openWeatherCurrent)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Card (
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ){ WeatherForecast(openWeatherForecast = openWeatherForecast, modifier = Modifier )}
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
fun WeatherIcon( icon : String,
                 modifier: Modifier)
{   AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(
                "https://openweathermap.org/img/w/" + icon + ".png"
                 )
                 .crossfade(true).build(),
                 error = painterResource(R.drawable.ic_broken_image),
                 placeholder = painterResource(R.drawable.loading_img),
                 contentDescription = stringResource(R.string.weathericon),
                 contentScale = ContentScale.FillBounds ,
                 modifier = modifier.size(80.dp, 80.dp)
        )
 }
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WeatherDetails ( openWeatherCurrent: OpenWeatherCurrent,
                     modifier: Modifier = Modifier)
{
    Row ()
    {
       Column (modifier.padding(end=4.dp)){
            Text("Weather",modifier.padding(end=44.dp))
            Text("Wind Speed",modifier.padding(end=19.dp))
            Text("Wind Direction",modifier.padding(end=2.dp))
            Text("Date",modifier.padding(end=70.dp))
            Text("Time",modifier.padding(end=68.dp))
        }
        Column (modifier.padding(end=8.dp)){
            Text(":")
            Text(":")
            Text(":")
            Text(":")
            Text(":")
        }
        Column(){
            Text(openWeatherCurrent.weatherCondition[0].summary)
            Text(""+ openWeatherCurrent.wind.speed + "m/s")
            Text("" +  openWeatherCurrent.wind.direction + " deg")
            Text(text = epochConvertToDate(openWeatherCurrent.datetime).toString())
            Text(text = epochConvertToTime(openWeatherCurrent.datetime).toString())
        }
    }
}
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ForecastItem(index : Int, openWeatherForecast: List<ForecastData>)
{
    val date : LocalDate = epochConvertToDate(openWeatherForecast[index].datetime)
   Card (
       modifier = Modifier
           .size(width = 64.dp, height = 170.dp)
           .fillMaxWidth()
    ) {
       Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.labelSmall,
           text = date.dayOfMonth.toString() + "-" + date.monthValue,
           modifier = Modifier.fillMaxWidth())
       Text(textAlign = TextAlign.Center,style = MaterialTheme.typography.labelSmall,
           text = epochConvertToTime(openWeatherForecast[index].datetime).toString(),
           modifier = Modifier.fillMaxWidth())
       WeatherIcon(openWeatherForecast[index].weatherCondition[0].icon, modifier = Modifier.size(64.dp, 64.dp) .padding(start=10.dp))
       Text(textAlign = TextAlign.Center,
            text = openWeatherForecast[index].weather.temperature.roundToInt().toString() + " \u2103",
            modifier = Modifier.fillMaxWidth() )
       Spacer(modifier = Modifier.height(6.dp))
       Text(textAlign = TextAlign.Center, text ="rain", modifier = Modifier.fillMaxWidth())
       Text(textAlign = TextAlign.Center, text = "" + (openWeatherForecast[index].precipitationProbability * 100).roundToInt() + " %",
               modifier = Modifier.fillMaxWidth()
            )
       }
}
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WeatherForecast (openWeatherForecast: OpenWeatherForecast, modifier: Modifier)
{
    LazyRow(
        modifier = modifier.padding(horizontal = 4.dp)
            .fillMaxWidth()
    )
    {
        items(openWeatherForecast.count)
        {
            ForecastItem (it, openWeatherForecast.forecast)
        }
    }
}