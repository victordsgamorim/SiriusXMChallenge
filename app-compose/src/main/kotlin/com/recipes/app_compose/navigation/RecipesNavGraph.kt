package com.recipes.app_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Search

    @Serializable
    data class Details(val id: String)
}

@Composable
fun RecipesNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Search) {
        searchPage(navController)
        detailsPage(navController)
    }
}


