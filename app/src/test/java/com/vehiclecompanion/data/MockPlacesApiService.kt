package com.vehiclecompanion.data

import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.data.remote.PlacesApiService
import com.vehiclecompanion.data.remote.PlacesResponse
import java.io.IOException

class MockPlacesApiService : PlacesApiService {

    var behavior: Behavior = Behavior.SUCCESS

    enum class Behavior {
        SUCCESS,
        EMPTY,
        ERROR
    }

    override suspend fun getPlaces(sw: String, ne: String, pageSize: Int): PlacesResponse {
        return when (behavior) {
            Behavior.SUCCESS -> {
                PlacesResponse(
                    places = listOf(
                        Place(
                            id = 1,
                            name = "The Hollywood Walk of Fame",
                            url = "https://www.roadtrippers.com/poi/1",
                            category = "Attraction",
                            rating = 5.0,
                            imageUrl = "https://www.roadtrippers.com/images/1",
                            loc = listOf(-118.3402, 34.1016)
                        ),
                        Place(
                            id = 2,
                            name = "Universal Studios Hollywood",
                            url = "https://www.roadtrippers.com/poi/2",
                            category = "Theme Park",
                            rating = 5.0,
                            imageUrl = "https://www.roadtrippers.com/images/2",
                            loc = listOf(-118.3533, 34.1381)
                        )
                    )
                )
            }
            Behavior.EMPTY -> {
                PlacesResponse(places = emptyList())
            }
            Behavior.ERROR -> {
                throw IOException("Simulated network error")
            }
        }
    }
}