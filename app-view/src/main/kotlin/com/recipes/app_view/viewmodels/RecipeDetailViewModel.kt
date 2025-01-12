package com.recipes.app_view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipes.app_view.uistate.RecipeDetailsUiState
import com.recipes.recipesdk.models.Result
import com.recipes.recipesdk.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: DetailsRepository,
) : ViewModel() {

    private var currentUiStateJob: Job? = null
    private val _recipeId = MutableStateFlow("")

    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun setRecipeId(recipeId: String) {
        _recipeId.update { recipeId }
    }

    fun getRecipeById() {
        currentUiStateJob?.cancel();
        currentUiStateJob = viewModelScope.launch {
            repository.getRecipe(_recipeId.value).collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> RecipeDetailsUiState.Loading
                    is Result.Success -> RecipeDetailsUiState.Success(result.data)
                    is Result.Error -> RecipeDetailsUiState.Error(result.message)
                }
            }
        }
    }
}