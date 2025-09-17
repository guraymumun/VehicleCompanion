package com.vehiclecompanion.ui.places.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.vehiclecompanion.data.remote.Place
import kotlinx.coroutines.launch

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun DefaultClustering(places: List<Place>, cameraPositionState: CameraPositionState, onInfoWindowClick: (Place) -> Unit) {
    val scope = rememberCoroutineScope()

    val clusteringItems = places.map {
        PlacesClusterItem(
            it.id,
            LatLng(it.loc[1], it.loc[0]),
            it.name,
            it.category
        )
    }

    Clustering(
        items = clusteringItems,
        onClusterClick = {
            val position = it.position
            scope.launch {
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(position, cameraPositionState.position.zoom + 2))
            }
            true
        },
        onClusterItemInfoWindowClick = { clusteringItem ->
            onInfoWindowClick(places.find { it.id == clusteringItem.id }!!)
            true
        }
    )
}

data class PlacesClusterItem(
    val id: Long,
    val itemPosition: LatLng,
    val itemTitle: String,
    val itemSnippet: String,
    val itemZIndex: Float = 0f,
) : ClusterItem {
    override fun getPosition(): LatLng =
        itemPosition

    override fun getTitle(): String =
        itemTitle

    override fun getSnippet(): String =
        itemSnippet

    override fun getZIndex(): Float =
        itemZIndex
}