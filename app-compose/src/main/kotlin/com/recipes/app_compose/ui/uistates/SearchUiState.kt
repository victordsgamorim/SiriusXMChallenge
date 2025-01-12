package com.recipes.app_compose.ui.uistates

import com.recipes.recipesdk.models.Recipe

sealed class SearchUiState {
    data object Empty : SearchUiState()
    data class Success(val recipes: List<Recipe>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
    data object Loading : SearchUiState()
}