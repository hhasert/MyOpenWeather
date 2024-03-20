package com.example.myopenweather.ui.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myopenweather.R
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherIcon( icon : String,
                 modifier: Modifier
)
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
@Composable
fun WindIcon ( direction : Float, modifier : Modifier)
{
    Icon(
        Icons.AutoMirrored.Filled.Send,
        null,
        modifier.rotate( direction - 270.0f)
    )
}
@RequiresApi(Build.VERSION_CODES.Q)
fun epochConvertToDate (epoch: Long ) : LocalDate
{
    return (LocalDate.parse(
        DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(epoch)),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))
}
@RequiresApi(Build.VERSION_CODES.Q)
fun epochConvertToTime ( epoch : Long ) : LocalTime
{
    return(LocalTime.parse(
        DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(epoch)),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))
}
