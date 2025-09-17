package com.vehiclecompanion.ui.garage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vehiclecompanion.data.local.vehicle.Vehicle
import com.vehiclecompanion.data.repo.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GarageViewModel @Inject constructor(
    private val repository: VehiclesRepository
) : ViewModel() {

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllVehicles().collect { vehicles ->
                _vehicles.value = vehicles
            }
        }
    }

    fun saveVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            repository.saveVehicle(vehicle)
        }
    }

    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            repository.deleteVehicle(vehicle)
        }
    }
}
