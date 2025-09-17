package com.vehiclecompanion.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

data class PlacesResponse(
    @SerializedName("pois")
    val places: List<Place> = emptyList()
)

data class Place(
    val id: Long,
    val name: String,
    val url: String,
    @SerializedName("primary_category_display_name")
    val category: String,
    val rating: Double,
    @SerializedName("v_320x320_url")
    val imageUrl: String,
    val loc: List<Double>
)

interface PlacesApiService {
    @GET("/api/v2/pois/discover")
    suspend fun getPlaces(
        @Query("sw_corner") sw: String,
        @Query("ne_corner") ne: String,
        @Query("page_size") pageSize: Int = 50
    ): PlacesResponse
}
