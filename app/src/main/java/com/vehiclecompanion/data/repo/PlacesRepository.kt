package com.vehiclecompanion.data.repo

import com.vehiclecompanion.data.local.place.FavoritePlace
import com.vehiclecompanion.data.local.place.FavoritePlaceDao
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.data.remote.PlacesApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val api: PlacesApiService,
    private val placeDao: FavoritePlaceDao
) {
    suspend fun getPlaces(swCorner: String, neCorner: String): Result<List<Place>> {
        return try {
            val response = api.getPlaces(swCorner, neCorner)
            Result.success(response.places)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveFavoritePlace(place: Place) {
        placeDao.insert(
            FavoritePlace(
                id = place.id,
                name = place.name,
                url = place.url,
                category = place.category,
                rating = place.rating,
                imageUrl = place.imageUrl,
                latitude = place.loc[1],
                longitude = place.loc[0]
            )
        )
    }

    suspend fun deleteFavoritePlace(place: Place) {
        placeDao.delete(place.id)
    }

    fun isFavorite(placeId: Long): Flow<Boolean> {
        return placeDao.isFavorite(placeId)
    }

    fun getFavoritePlaces(): Flow<List<FavoritePlace>> {
        return placeDao.getAllFavoritePlaces()
    }
}
