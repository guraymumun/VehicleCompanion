package com.vehiclecompanion.data.local.place

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_places")
data class FavoritePlace(
    @PrimaryKey
    val id: Long,
    val name: String,
    val url: String,
    val category: String,
    val rating: Double,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)