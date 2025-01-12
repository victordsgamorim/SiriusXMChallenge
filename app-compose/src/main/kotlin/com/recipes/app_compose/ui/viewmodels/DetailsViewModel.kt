package com.recipes.app_compose.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.recipes.app_compose.navigation.Destination
import com.recipes.app_compose.ui.uistates.DetailsUiState
import com.recipes.recipesdk.models.Result
import com.recipes.recipesdk.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val recipeId = savedStateHandle.toRoute<Destination.Details>().id

    private var currentUiStateJob: Job? = null
    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getRecipeById()
    }

    private fun getRecipeById() {
        currentUiStateJob?.cancel();
        currentUiStateJob = viewModelScope.launch {
            repository.getRecipe(recipeId).collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> DetailsUiState.Loading
                    is Result.Success -> DetailsUiState.Success(result.data)
                    is Result.Error -> DetailsUiState.Error(result.message)
                }
            }
        }


    }

}
