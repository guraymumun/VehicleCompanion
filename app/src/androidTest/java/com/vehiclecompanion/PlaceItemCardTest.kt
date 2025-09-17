package com.vehiclecompanion

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.vehiclecompanion.data.remote.Place
import com.vehiclecompanion.data.repo.PlacesRepository
import com.vehiclecompanion.ui.places.PlacesScreen
import com.vehiclecompanion.ui.places.PlacesViewModel
import com.vehiclecompanion.ui.places.list.PlaceItemCard
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@HiltAndroidTest
@RunWith(JUnit4::class)
class PlaceItemCardTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun placeCard_displaysCorrectContent() {
        val testPlace = Place(
            name = "Test Place",
            rating = 4.5,
            imageUrl = "https://example.com/image.jpg",
            category = "A great test place",
            id = 1L,
            url = "https://example.com/image.jpg",
            loc = emptyList()
        )

        composeTestRule.setContent {
            PlaceItemCard(place = testPlace, onClick = {})
        }

        // Verify that the fields are displayed
        composeTestRule.onNodeWithText("Test Place").assertIsDisplayed()
        composeTestRule.onNodeWithText("A great test place").assertIsDisplayed()
    }
}