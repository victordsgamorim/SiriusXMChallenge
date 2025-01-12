package com.recipes.app_compose.ui.page.search

import SearchTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recipes.app_compose.ui.component.LoadingWidget
import com.recipes.app_compose.ui.component.SearchResultItem
import com.recipes.app_compose.ui.component.SearchResultItemUiState
import com.recipes.app_compose.ui.page.search.SearchScreenDefaults.HorizontalPadding
import com.recipes.app_compose.ui.page.search.SearchScreenDefaults.VerticalPadding
import com.recipes.app_compose.ui.uistates.SearchUiState
import com.recipes.recipesdk.models.getSampleRecipes

private object SearchScreenDefaults {
    val VerticalPadding = 16.dp
    val HorizontalPadding = 24.dp
    val ErrorTopPadding = 64.dp
}

@Composable
fun SearchScreen(
    onRecipeClicked: (itemId: String) -> Unit,
    uiState: SearchUiState = SearchUiState.Empty,
    onSearch: (query: String) -> Unit,
    onDelete: () -> Unit,
) {

    var searchText by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()

    ) {
        Text(
            text = "Recipes",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = HorizontalPadding)
        )
        Column(
            modifier = Modifier.padding(
                vertical = VerticalPadding,
                horizontal = HorizontalPadding
            )
        ) {
            SearchTextField(value = searchText, placeholder = "Search...", onValueChange = { text ->
                searchText = text
                onSearch(text)
            }, onDeleteTextTap = {
                searchText = ""
                onDelete()
            })
            when (uiState) {
                is SearchUiState.Empty -> {}
                is SearchUiState.Loading -> {
                    LoadingWidget()
                }

                is SearchUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.padding(vertical = VerticalPadding),
                    ) {
                        items(uiState.recipes) { recipe ->
                            SearchResultItem(
                                state = SearchResultItemUiState(
                                    id = recipe.recipeId,
                                    title = recipe.title,
                                    subtitle = recipe.publisher,
                                    imageUrl = recipe.imageUrl,
                                ),
                                onClick = {
                                    onRecipeClicked(recipe.recipeId)
                                }
                            )
                        }
                    }
                }

                is SearchUiState.Error -> {
                    Text(
                        uiState.message,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = SearchScreenDefaults.ErrorTopPadding)
                    )
                }
            }
        }


    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        uiState = SearchUiState.Success(getSampleRecipes()),
        onRecipeClicked = {},
        onSearch = {},
        onDelete = {},
    )
}

