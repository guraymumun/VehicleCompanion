package com.vehiclecompanion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vehiclecompanion.data.local.place.FavoritePlace
import com.vehiclecompanion.data.local.place.FavoritePlaceDao
import com.vehiclecompanion.data.local.vehicle.Vehicle
import com.vehiclecompanion.data.local.vehicle.VehicleDao

@Database(entities = [Vehicle::class, FavoritePlace::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun favoritePlaceDao(): FavoritePlaceDao
}
