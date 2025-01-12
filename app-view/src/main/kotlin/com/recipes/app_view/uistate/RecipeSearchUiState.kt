package com.recipes.app_view.uistate

import com.recipes.recipesdk.models.Recipe

sealed class RecipeSearchUiState {
    data object Empty : RecipeSearchUiState()
    data class Success(val recipes: List<Recipe>) : RecipeSearchUiState()
    data class Error(val message: String) : RecipeSearchUiState()
    data object Loading : RecipeSearchUiState()
}