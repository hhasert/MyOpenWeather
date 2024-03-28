package com.example.myopenweather.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myopenweather.ui.viewmodel.AppViewModelProvider
import com.example.myopenweather.ui.viewmodel.LocationEntryViewModel
import com.example.myopenweather.ui.viewmodel.LocationUiState
import kotlinx.coroutines.launch

//TODO Add screen for adding location and interact with the database

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationAddScreen (
    navigateBack: () -> Unit,
    viewModel: LocationEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{    val coroutineScope = rememberCoroutineScope()

        LocationEntryBody(
            locationUiState = viewModel.locationUiState,
            onSaveClick = {
                // Note: If the user rotates the screen very fast, the operation may get cancelled
                // and the item may not be saved in the Database. This is because when config
                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                // be cancelled - since the scope is bound to composition.
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
}
@Composable
fun LocationEntryBody(
    locationUiState: LocationUiState,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

}

