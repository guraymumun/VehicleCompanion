package com.vehiclecompanion.ui.garage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vehiclecompanion.R
import com.vehiclecompanion.data.local.vehicle.Vehicle

@Composable
fun VehicleItemCard(vehicle: Vehicle, onClick: () -> Unit, onDeleteClick: (Vehicle) -> Unit) {

    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(
                    model = vehicle.imageUrl,
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground)
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(text = vehicle.name)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${vehicle.make} ${vehicle.model} • ${vehicle.year}")
            }
            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                IconButton(
                    onClick = { showMenu = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            onDeleteClick(vehicle)
                            showMenu = false
                        }
                    )
                }
            }
        }
    }
}