package com.example.myopenweather.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myopenweather.model.OpenWeatherCurrent
import com.example.myopenweather.model.OpenWeatherForecast
import com.example.myopenweather.ui.common.ErrorScreen
import com.example.myopenweather.ui.common.LoadingScreen
import com.example.myopenweather.ui.components.WeatherForecast
import com.example.myopenweather.ui.components.WeatherInfo
import com.example.myopenweather.ui.viewmodel.OpenWeatherCurrentUiState
import com.example.myopenweather.ui.viewmodel.OpenWeatherForecastUiState

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
                    modifier = Modifier
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
    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    )
    {
        WeatherInfo(openWeatherCurrent)
        Spacer(modifier = Modifier.height(32.dp))
        WeatherForecast(openWeatherForecast = openWeatherForecast, modifier = modifier)
    }
}

