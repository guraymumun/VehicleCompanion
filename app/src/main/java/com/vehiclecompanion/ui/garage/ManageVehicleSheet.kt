package com.vehiclecompanion.ui.garage

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.vehiclecompanion.R
import com.vehiclecompanion.data.local.vehicle.Vehicle
import com.vehiclecompanion.ui.common.DefaultButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageVehicleSheet(
    vehicle: Vehicle?,
    onDismiss: () -> Unit,
    onSave: (Vehicle) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Check if editing a vehicle and set the initial image URI
    if (vehicle != null && vehicle.imageUrl != null) {
        imageUri = vehicle.imageUrl.toUri()
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(it, takeFlags)
                imageUri = it
            }
        }
    )

    var name by remember { mutableStateOf(vehicle?.name ?: "") }
    var make by remember { mutableStateOf(vehicle?.make ?: "") }
    var model by remember { mutableStateOf(vehicle?.model ?: "") }
    var year by remember { mutableStateOf(vehicle?.year ?: "") }
    var vin by remember { mutableStateOf(vehicle?.vin ?: "") }
    var fuelType by remember { mutableStateOf(vehicle?.fuelType ?: "") }

    var isNameValid by remember { mutableStateOf(false) }
    var isMakeValid by remember { mutableStateOf(false) }
    var isModelValid by remember { mutableStateOf(false) }
    var isYearValid by remember { mutableStateOf(false) }

    // Track if fields have been touched
    var nameFocused by remember { mutableStateOf(false) }
    var makeFocused by remember { mutableStateOf(false) }
    var modelFocused by remember { mutableStateOf(false) }
    var yearFocused by remember { mutableStateOf(false) }

    // Validate inputs
    LaunchedEffect(name, make, model, year) {
        isNameValid = name.isNotBlank()
        isMakeValid = make.isNotBlank()
        isModelValid = model.isNotBlank()
        isYearValid = year.isNotBlank() && year.matches(Regex("\\d{4}"))
    }

    val isFormValid = isNameValid && isMakeValid && isModelValid && isYearValid

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .background(color = colorResource(R.color.color_gray_light))
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri == null) {
                    Button(onClick = { pickImageLauncher.launch("image/*") }) {
                        Text("Select Image")
                    }
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = imageUri,
                            error = painterResource(R.drawable.ic_launcher_foreground),
                            placeholder = painterResource(R.drawable.ic_launcher_foreground)
                        ),
                        contentDescription = "Vehicle Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        onClick = { imageUri = null }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Remove the selected image",
                        )
                    }
                }
            }

            Text(
                text = if (vehicle == null) "Add Vehicle" else "Edit Vehicle",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name*") },
                isError = !isNameValid && nameFocused,
                modifier = Modifier.fillMaxWidth().onFocusChanged {
                    if (it.isFocused) nameFocused = true
                },
                maxLines = 1
            )
            if (!isNameValid && nameFocused) {
                Text(text = "Name is required", color = MaterialTheme.colorScheme.error)
            }
            Spacer(Modifier.height(8.dp))

            TextField(
                value = make,
                onValueChange = { make = it },
                label = { Text("Make*") },
                isError = !isMakeValid && makeFocused,
                modifier = Modifier.fillMaxWidth().onFocusChanged {
                    if (it.isFocused) makeFocused = true
                },
                maxLines = 1
            )
            if (!isMakeValid && makeFocused) {
                Text(text = "Make is required", color = MaterialTheme.colorScheme.error)
            }
            Spacer(Modifier.height(8.dp))

            TextField(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model*") },
                isError = !isModelValid && modelFocused,
                modifier = Modifier.fillMaxWidth().onFocusChanged {
                    if (it.isFocused) modelFocused = true
                },
                maxLines = 1
            )
            if (!isModelValid && modelFocused) {
                Text(text = "Model is required", color = MaterialTheme.colorScheme.error)
            }
            Spacer(Modifier.height(8.dp))

            TextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year*") },
                isError = !isYearValid && yearFocused,
                modifier = Modifier.fillMaxWidth().onFocusChanged {
                    if (it.isFocused) yearFocused = true
                },
                maxLines = 1
            )
            if (!isYearValid && yearFocused) {
                Text(text = "Year must be a 4-digit number", color = MaterialTheme.colorScheme.error)
            }
            Spacer(Modifier.height(8.dp))

            TextField(value = vin, onValueChange = { vin = it }, label = { Text("VIN") }, modifier = Modifier.fillMaxWidth(), maxLines = 1)
            Spacer(Modifier.height(8.dp))
            TextField(value = fuelType, onValueChange = { fuelType = it }, label = { Text("Fuel Type") }, modifier = Modifier.fillMaxWidth(), maxLines = 1)

            Spacer(Modifier.height(16.dp))

            DefaultButton(text = "Save", enabled = isFormValid, onClick = {
                val newVehicle = Vehicle(
                    id = vehicle?.id ?: 0,
                    name = name,
                    make = make,
                    model = model,
                    year = year,
                    vin = vin.ifEmpty { null },
                    fuelType = fuelType.ifEmpty { null },
                    imageUrl = imageUri?.toString()
                )
                onSave(newVehicle)
            })
        }
    }
}