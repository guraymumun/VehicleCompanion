package com.vehiclecompanion.ui.places.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.vehiclecompanion.data.remote.Place

@Composable
fun PlacesList(places: List<Place>, onPlaceClick: (Place) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(places, key = { it.id }) { place ->
            PlaceItemCard(place = place) {
                onPlaceClick(place)
            }
        }
    }
}