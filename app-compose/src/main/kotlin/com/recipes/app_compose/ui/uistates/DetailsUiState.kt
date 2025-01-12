package com.recipes.app_compose.ui.uistates

import com.recipes.recipesdk.models.Recipe


sealed class DetailsUiState {
    data class Success(val recipe: Recipe) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
    data object Loading : DetailsUiState()
}