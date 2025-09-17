package com.vehiclecompanion.ui.places.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.ui.common.ToggleFavoriteButton
import com.vehiclecompanion.ui.places.PlacesViewModel

@Composable
fun PlaceItemCard(
    viewModel: PlacesViewModel = hiltViewModel(),
    place: Place, onClick: (Place) -> Unit = {}
) {
    val isFavorite by viewModel.isFavorite(place.id).collectAsStateWithLifecycle(false)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(place)
            }
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(place.imageUrl)
                    .crossfade(true)
                    .setHeader("User-Agent", "Mozilla/5.0") // Used to prevent 403 errors from some servers (for example the image of the second item in the list)
                    .memoryCachePolicy(
                        CachePolicy.ENABLED
                    )
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp),
                contentDescription = null
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 8.dp)
                ) {
                    Text(
                        text = place.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = place.category,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    RatingBar(rating = place.rating)

                }

                ToggleFavoriteButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    place = place
                )
            }
        }
    }
}

@Composable
fun RatingBar(rating: Double) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) Color.Yellow else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}