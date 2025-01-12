package com.recipes.app_compose.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.recipes.app_compose.ui.page.search.SearchScreen
import com.recipes.app_compose.ui.viewmodels.SearchViewModel


fun NavGraphBuilder.searchPage(navController: NavHostController) {
    composable<Destination.Search> {
        val viewModel = hiltViewModel<SearchViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        SearchScreen(
            uiState = uiState,
            onRecipeClicked = { itemId -> navController.navigateToDetails(itemId) },
            onSearch = { query -> viewModel.searchRecipes(query) },
            onDelete = { viewModel.deleteSearchText() }
        )
    }
}