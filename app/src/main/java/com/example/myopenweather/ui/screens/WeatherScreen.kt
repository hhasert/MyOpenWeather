package com.example.myopenweather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myopenweather.model.OpenWeatherCurrent
import com.example.myopenweather.model.OpenWeatherForecast

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
) {
    Column {
        CurrentWeatherInfo(openWeatherCurrent, modifier)
        Spacer(modifier = Modifier.height(32.dp))
        WeatherForecast(openWeatherForecast = openWeatherForecast, modifier = modifier)
    }
}

