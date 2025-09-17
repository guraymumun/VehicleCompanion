package com.vehiclecompanion.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.data.repo.PlacesRepository
import com.vehiclecompanion.ui.places.model.PlacesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlacesUiState())
    val uiState: StateFlow<PlacesUiState> = _uiState.asStateFlow()

    init {
        fetchPlaces()
    }

    fun fetchPlaces() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isError = false)
            try {
                // Fixed coordinates for the initial request, as specified in the project brief
                placesRepository.getPlaces("-84.540499,39.079888", "-84.494260,39.113254")
                    .onSuccess {
                        _uiState.value = _uiState.value.copy(
                            places = it,
                            isLoading = false
                        )
                    }.onFailure {
                        _uiState.value = _uiState.value.copy(
                            isError = true,
                            isLoading = false
                        )
                    }
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isError = true
                )
            }
        }
    }

    fun toggleFavorite(place: Place) {
        viewModelScope.launch {
            val isFavorite = placesRepository.isFavorite(place.id).first()
            if (isFavorite) {
                placesRepository.deleteFavoritePlace(place)
            } else {
                placesRepository.saveFavoritePlace(place)
            }
        }
    }

    fun isFavorite(placeId: Long): Flow<Boolean> {
        return placesRepository.isFavorite(placeId)
    }

    fun getFavoritePlaces(): Flow<List<Place>> {
        return placesRepository.getFavoritePlaces().let { flow ->
            flow.map { favoritePlaces ->
                favoritePlaces.map { fav ->
                    Place(
                        id = fav.id,
                        name = fav.name,
                        url = fav.url,
                        category = fav.category,
                        rating = fav.rating,
                        imageUrl = fav.imageUrl,
                        loc = listOf(fav.longitude, fav.latitude)
                    )
                }
            }
        }
    }
}