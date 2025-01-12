package com.recipes.app_view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipes.app_view.uistate.RecipeSearchUiState
import com.recipes.recipesdk.models.Result
import com.recipes.recipesdk.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel() {

    private var currentUiStateJob: Job? = null
    private val _uiState = MutableStateFlow<RecipeSearchUiState>(RecipeSearchUiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun searchRecipes(query: String) {
        currentUiStateJob?.cancel()
        currentUiStateJob = viewModelScope.launch {
            repository.searchRecipe(query)
                .collectLatest { result ->
                    _uiState.value = when (result) {
                        is Result.Loading -> RecipeSearchUiState.Loading
                        is Result.Success -> RecipeSearchUiState.Success(result.data)
                        is Result.Error -> RecipeSearchUiState.Error(result.message)
                    }

                }
        }
    }

    fun deleteSearchText() {
        currentUiStateJob?.cancel()
        currentUiStateJob = viewModelScope.launch {
            _uiState.update { RecipeSearchUiState.Empty }
        }
    }
}
