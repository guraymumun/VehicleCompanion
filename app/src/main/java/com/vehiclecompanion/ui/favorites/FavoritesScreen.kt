package com.vehiclecompanion.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.ui.common.EmptyState
import com.vehiclecompanion.ui.places.PlaceDetailScreen
import com.vehiclecompanion.ui.places.PlacesViewModel
import com.vehiclecompanion.ui.places.list.PlaceItemCard

@Composable
fun FavoritesScreen(
    viewModel: PlacesViewModel = hiltViewModel()
) {
    val places by viewModel.getFavoritePlaces().collectAsStateWithLifecycle(emptyList())
    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    if (places.isEmpty()) {
        EmptyState("Your favorite list is empty.")
    } else {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(places, key = { it.id }) { place ->
                PlaceItemCard(place = place) {
                    selectedPlace = place
                }
            }
        }
    }

    selectedPlace?.let {
        PlaceDetailScreen(place = it) {
            selectedPlace = null
        }
    }
}
