package com.vehiclecompanion.data.local.place

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoritePlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: FavoritePlace)

    @Query("DELETE FROM favorite_places WHERE id = :placeId")
    suspend fun delete(placeId: Long)

    @Query("SELECT EXISTS(SELECT * FROM favorite_places WHERE id = :placeId)")
    fun isFavorite(placeId: Long): Flow<Boolean>

    @Query("SELECT * FROM favorite_places")
    fun getAllFavoritePlaces(): Flow<List<FavoritePlace>>
}