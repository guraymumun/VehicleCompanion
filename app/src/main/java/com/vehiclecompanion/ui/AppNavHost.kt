package com.vehiclecompanion.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vehiclecompanion.ui.places.PlacesScreen
import com.vehiclecompanion.ui.garage.GarageScreen
import com.vehiclecompanion.ui.favorites.FavoritesScreen
import java.util.Locale

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val items = listOf("garage", "places", "favorites")

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val current = navBackStackEntry.value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = current == screen,
                        onClick = { navController.navigate(screen) { launchSingleTop = true } },
                        icon = {},
                        label = { Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = items[0], modifier = Modifier.padding(innerPadding)) {
            composable(items[0]) { GarageScreen() }
            composable(items[1]) { PlacesScreen() }
            composable(items[2]) { FavoritesScreen() }
        }
    }
}
