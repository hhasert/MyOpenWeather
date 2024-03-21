package com.example.myopenweather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myopenweather.data.LocationData


@Composable
fun LocationsList (locations : List <LocationData>, onClick : (location: LocationData) -> Unit, modifier: Modifier){
         LazyColumn(
            modifier = modifier.padding(horizontal = 4.dp)
                .fillMaxWidth()
                .height(4000.dp)
        )
        {
            items(locations.count()) {
                LocationItem(locations[it], onClick)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
}
@Composable
fun LocationItem(location: LocationData, onClick : (location: LocationData) -> Unit)
{
    Card ( onClick = {onClick (location)},
           modifier = Modifier
                     .height( 65.dp)
                     .fillMaxWidth()
     )
    {
        Row ( horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                                .padding (4.dp)
       )
        { Text (style = MaterialTheme.typography.displayMedium, text = location.name ) }
        Row (   horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
                     .padding (4.dp)
        ) {
            Text (style = MaterialTheme.typography.labelSmall, text = "Latitude : " + location.latitude )
            Text (style = MaterialTheme.typography.labelSmall, text = "longitude : " + location.longitude )
        }
    }
}