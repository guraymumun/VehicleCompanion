package com.vehiclecompanion.ui.places.model

import com.vehiclecompanion.data.remote.Place

data class PlacesUiState(
    val places: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)