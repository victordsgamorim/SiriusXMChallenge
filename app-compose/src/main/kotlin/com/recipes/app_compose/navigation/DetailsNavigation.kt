package com.recipes.app_compose.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.recipes.app_compose.ui.page.details.DetailsScreen
import com.recipes.app_compose.ui.viewmodels.DetailsViewModel

fun NavGraphBuilder.detailsPage(navController: NavHostController) {
    composable<Destination.Details> { backStackEntry ->
        val viewModel = hiltViewModel<DetailsViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val itemId = (backStackEntry.toRoute<Destination.Details>() as? Destination.Details)?.id
        itemId?.let {
            DetailsScreen(
                uiState = uiState,
                onBackPressed = { navController.popBackStack() },
                onErrorCallback = { navController.popBackStack() }
            )
        }
    }
}

fun NavController.navigateToDetails(id: String, navOptions: NavOptions? = null) {
    navigate(Destination.Details(id = id), navOptions)
}