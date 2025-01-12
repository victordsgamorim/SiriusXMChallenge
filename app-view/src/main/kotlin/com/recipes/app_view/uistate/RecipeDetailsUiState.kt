package com.recipes.app_view.uistate

import com.recipes.recipesdk.models.Recipe

sealed class RecipeDetailsUiState {
    data class Success(val recipe: Recipe) : RecipeDetailsUiState()
    data class Error(val message: String) : RecipeDetailsUiState()
    data object Loading : RecipeDetailsUiState()
}