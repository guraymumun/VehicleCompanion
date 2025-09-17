package com.vehiclecompanion.ui.places

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.maps.android.compose.rememberCameraPositionState
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.ui.common.EmptyState
import com.vehiclecompanion.ui.common.ErrorState
import com.vehiclecompanion.ui.common.LoadingState
import com.vehiclecompanion.ui.places.list.PlacesList
import com.vehiclecompanion.ui.places.map.PlacesMap

@Composable
fun PlacesScreen(
    viewModel: PlacesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    val pagerState = rememberPagerState { 2 }
    val cameraPositionState = rememberCameraPositionState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                LoadingState()
            }

            uiState.isError -> {
                ErrorState("Failed to load places. Please try again.")
            }

            uiState.places.isNotEmpty() -> {
                Column {
                    PlacesTabs(pagerState = pagerState)
                    HorizontalPager(state = pagerState, beyondViewportPageCount = 1, userScrollEnabled = pagerState.currentPage != 1) { page ->
                        when (page) {
                            0 -> {
                                PlacesList(uiState.places) {
                                    selectedPlace = it
                                }
                            }

                            1 -> {
                                PlacesMap(uiState.places, cameraPositionState) {
                                    selectedPlace = it
                                }
                            }
                        }
                    }
                }
            }

            else -> {
                EmptyState("No places found in this area.")
            }
        }
    }

    selectedPlace?.let {
        PlaceDetailScreen(place = it) {
            selectedPlace = null
        }
    }
}
