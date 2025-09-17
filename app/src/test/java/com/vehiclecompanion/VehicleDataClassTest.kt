package com.vehiclecompanion

import com.vehiclecompanion.data.local.vehicle.Vehicle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class VehicleDataClassTest {

    @Test
    fun `vehicle creation with all required fields is correct`() {
        val imageUrl = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000018"
        val vehicle = Vehicle(
            id = 1,
            name = "My Truck",
            make = "Ford",
            model = "F-150",
            year = "2022",
            vin = "1234567890ABCDEFG",
            fuelType = "Gasoline",
            imageUrl = imageUrl
        )

        assertEquals(1, vehicle.id)
        assertEquals("My Truck", vehicle.name)
        assertEquals("Ford", vehicle.make)
        assertEquals("F-150", vehicle.model)
        assertEquals("2022", vehicle.year)
        assertEquals("1234567890ABCDEFG", vehicle.vin)
        assertEquals("Gasoline", vehicle.fuelType)
    }

    @Test
    fun `vehicle creation with optional fields as null is correct`() {
        val vehicle = Vehicle(
            id = 2,
            name = "My Sedan",
            make = "Honda",
            model = "Civic",
            year = "2020",
            vin = null,
            fuelType = null,
            imageUrl = null
        )

        assertEquals(2, vehicle.id)
        assertEquals("My Sedan", vehicle.name)
        assertEquals("Honda", vehicle.make)
        assertEquals("Civic", vehicle.model)
        assertEquals("2020", vehicle.year)
        assertNull(vehicle.vin)
        assertNull(vehicle.fuelType)
    }

    @Test
    fun `vehicle creation with image url is correct`() {
        val imageUrl = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000018"
        val vehicle = Vehicle(
            id = 3,
            name = "My Electric Car",
            make = "Tesla",
            model = "Model 3",
            year = "2023",
            vin = "TESLA123456789",
            fuelType = "Electric",
            imageUrl = imageUrl
        )

        assertEquals("My Electric Car", vehicle.name)
        assertEquals("Tesla", vehicle.make)
        assertEquals("Model 3", vehicle.model)
        assertEquals("2023", vehicle.year)
        assertEquals("TESLA123456789", vehicle.vin)
        assertEquals("Electric", vehicle.fuelType)
        assertEquals(imageUrl, vehicle.imageUrl)
    }
}