package com.vehiclecompanion.ui.places.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.vehiclecompanion.data.remote.Place

@Composable
fun PlacesMap(places: List<Place>, cameraPositionState: CameraPositionState, onInfoWindowClick: (Place) -> Unit) {

    val initialPosition = LatLng(places[0].loc[1], places[0].loc[0])
    cameraPositionState.position = CameraPosition.fromLatLngZoom(initialPosition, 12f)

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        DefaultClustering(
            places,
            cameraPositionState,
            onInfoWindowClick
        )
    }
}