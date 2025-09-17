package com.vehiclecompanion.ui.places

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.ui.common.DefaultButton
import com.vehiclecompanion.ui.common.ToggleFavoriteButton
import com.vehiclecompanion.ui.places.list.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(place: Place, onDismiss: () -> Unit) {

    val context = LocalContext.current
    val placeCoordinates = LatLng(place.loc[1], place.loc[0])
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(placeCoordinates, 15f)
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = place.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = place.category,
                style = MaterialTheme.typography.bodyLarge
            )
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Spacer(Modifier.height(8.dp))
                    RatingBar(rating = place.rating)
                    Spacer(Modifier.height(8.dp))
                }
                ToggleFavoriteButton(place = place)
            }
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(scrollGesturesEnabled = false)
            ) {
                Marker(
                    state = rememberUpdatedMarkerState(position = placeCoordinates),
                    title = place.name,
                    snippet = place.category
                )
            }

            Spacer(Modifier.weight(1f))

            DefaultButton(
                text = "Open in Browser",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, place.url.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }
}
