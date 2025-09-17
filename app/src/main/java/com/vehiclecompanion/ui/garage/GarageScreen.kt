package com.vehiclecompanion.ui.garage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vehiclecompanion.data.local.vehicle.Vehicle

@Composable
fun GarageScreen(viewModel: GarageViewModel = hiltViewModel()) {

    val vehicles by viewModel.vehicles.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }
    var vehicleToEdit by remember { mutableStateOf<Vehicle?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (vehicles.isEmpty()) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                text = "Your garage is empty.\nAdd your first vehicle to get started!",
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(vehicles, key = { it.id }) { vehicle ->
                    VehicleItemCard(
                        vehicle = vehicle,
                        onClick = {
                            vehicleToEdit = vehicle
                            showSheet = true
                        },
                        onDeleteClick = {
                            viewModel.deleteVehicle(it)
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                vehicleToEdit = null
                showSheet = true
            }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Vehicle")
        }
    }

    if (showSheet) {
        ManageVehicleSheet(
            vehicle = vehicleToEdit,
            onDismiss = { showSheet = false },
            onSave = { vehicle ->
                viewModel.saveVehicle(vehicle)
                showSheet = false
            }
        )
    }
}
