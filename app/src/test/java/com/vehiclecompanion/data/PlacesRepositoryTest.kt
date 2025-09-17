package com.vehiclecompanion.data

import com.vehiclecompanion.data.local.place.FavoritePlaceDao
import com.vehiclecompanion.data.repo.PlacesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.io.IOException

class PlacesRepositoryTest {

    private lateinit var mockApiService: MockPlacesApiService
    private lateinit var mockPlaceDao: FavoritePlaceDao
    private lateinit var repository: PlacesRepository

    @Before
    fun setup() {
        mockApiService = MockPlacesApiService()
        mockPlaceDao = mock()
        repository = PlacesRepository(mockApiService, mockPlaceDao)
    }

    @Test
    fun `getPlaces returns success on successful API response`() = runTest {
        mockApiService.behavior = MockPlacesApiService.Behavior.SUCCESS

        val result = repository.getPlaces("sw_corner", "ne_corner")

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrThrow().size)
    }

    @Test
    fun `getPlaces returns empty list on empty API response`() = runTest {
        mockApiService.behavior = MockPlacesApiService.Behavior.EMPTY

        val result = repository.getPlaces("sw_corner", "ne_corner")

        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrThrow().size)
    }

    @Test
    fun `getPlaces returns failure on network exception`() = runTest {
        // Given
        mockApiService.behavior = MockPlacesApiService.Behavior.ERROR

        // When
        val result = repository.getPlaces("sw_corner", "ne_corner")

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is IOException)
        assertEquals("Simulated network error", exception?.message)
    }
}