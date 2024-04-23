package com.example.myopenweather.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myopenweather.R
import com.example.myopenweather.ui.viewmodel.AppViewModelProvider
import com.example.myopenweather.ui.viewmodel.LocationEntryViewModel
import com.example.myopenweather.ui.viewmodel.LocationName

import kotlinx.coroutines.launch

//TODO Add screen for adding location and interact with the database


@Composable
fun LocationAddScreen (
    navigateBack: () -> Unit,
    viewModel: LocationEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{    val coroutineScope = rememberCoroutineScope()

        LocationEntryBody(
            locationName = viewModel.locationNameUiState.location,
            onLocationValueChange = viewModel::updateUiState,
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
    locationName: LocationName,
    onLocationValueChange: (LocationName) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
       LocationInputForm(
           location = locationName,
           onValueChange = onLocationValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {},
            enabled = true,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}
@Composable
fun LocationInputForm(
    modifier: Modifier = Modifier,
    location : LocationName,
    onValueChange: (LocationName) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = location.name,
            onValueChange = { onValueChange(location.copy(name = it)) },
            label = { Text(stringResource(R.string.location_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            readOnly = false
        )
    }
}