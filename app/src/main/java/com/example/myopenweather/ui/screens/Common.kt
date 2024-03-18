package com.example.myopenweather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
@RequiresApi(Build.VERSION_CODES.Q)
fun epochConvertToDate (epoch: Long ) : String
{
    return (LocalDate.parse(DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(epoch))
        , DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")).toString())
}
@RequiresApi(Build.VERSION_CODES.Q)
fun epochConvertToTime ( epoch : Long ) : String
{
    return(LocalTime.parse(DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(epoch))
        , DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")).toString())
}

