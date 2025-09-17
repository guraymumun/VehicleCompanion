package com.vehiclecompanion.data.local.vehicle

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val make: String,
    val model: String,
    val year: String,
    val vin: String?,
    val fuelType: String?,
    val imageUrl: String?
)
