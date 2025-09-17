package com.vehiclecompanion.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.ui.places.PlacesViewModel

@Composable
fun ToggleFavoriteButton(
    modifier: Modifier = Modifier,
    place: Place,
    viewModel: PlacesViewModel = hiltViewModel()
) {
    val isFavorite by viewModel.isFavorite(place.id).collectAsStateWithLifecycle(false)

    IconButton(
        modifier = modifier,
        onClick = { viewModel.toggleFavorite(place) }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}