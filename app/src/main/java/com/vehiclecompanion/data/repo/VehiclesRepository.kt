package com.vehiclecompanion.data.repo

import com.vehiclecompanion.data.local.vehicle.Vehicle
import com.vehiclecompanion.data.local.vehicle.VehicleDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class VehiclesRepository @Inject constructor(
    private val vehicleDao: VehicleDao
) {
    fun getAllVehicles(): Flow<List<Vehicle>> = vehicleDao.getAllVehicles()

    suspend fun saveVehicle(vehicle: Vehicle) {
        if (vehicle.id == 0) {
            vehicleDao.insert(vehicle)
        } else {
            vehicleDao.update(vehicle)
        }
    }

    suspend fun deleteVehicle(vehicle: Vehicle) {
        vehicleDao.deleteVehicle(vehicle.id)
    }
}