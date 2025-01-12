package com.recipes.app_compose.ui.page.details

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.recipes.app_compose.ui.component.AppBar
import com.recipes.app_compose.ui.component.Dialog
import com.recipes.app_compose.ui.component.LoadingWidget
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.HorizontalPadding
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.ImageHeight
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.IngredientVerticalPadding
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.IngredientsHeaderBottomPadding
import com.recipes.app_compose.ui.page.details.DetailsScreenDefaults.VerticalPadding
import com.recipes.app_compose.ui.theme.DarkGreen
import com.recipes.app_compose.ui.uistates.DetailsUiState

private object DetailsScreenDefaults {
    val VerticalPadding = 16.dp
    val HorizontalPadding = 16.dp
    val IngredientVerticalPadding = 2.dp
    val IngredientsHeaderBottomPadding = 8.dp
    val ImageHeight = 250.dp
}

@Composable
fun DetailsScreen(
    uiState: DetailsUiState = DetailsUiState.Loading,
    onBackPressed: () -> Unit,
    onErrorCallback: () -> Unit
) {
    var isIngredientsVisible by rememberSaveable { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    when (uiState) {
        is DetailsUiState.Loading -> LoadingWidget()
        is DetailsUiState.Success -> {
            val recipe = uiState.recipe
            LaunchedEffect(Unit) {
                isIngredientsVisible = true
            }
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                AppBar(title = recipe.title, onBackPressed = onBackPressed)
                if (isPortrait) {
                    RecipeImage(url = recipe.imageUrl, height = ImageHeight)
                    RecipeInformation(
                        title = recipe.title,
                        socialRank = recipe.socialRank,
                        ingredients = recipe.ingredients,
                        isIngredientsVisible = isIngredientsVisible
                    )
                } else {
                    Row {
                        RecipeImage(modifier = Modifier.weight(1f), url = recipe.imageUrl)
                        RecipeInformation(
                            modifier = Modifier.weight(1f),
                            title = recipe.title,
                            socialRank = recipe.socialRank,
                            ingredients = recipe.ingredients,
                            isIngredientsVisible = isIngredientsVisible
                        )
                    }
                }

            }
        }

        is DetailsUiState.Error -> {
            Dialog(
                enabled = true,
                title = "Ops, something went wrong!",
                text = uiState.message,
                onDismiss = onErrorCallback,
            )
        }
    }

}

@Composable
private fun RecipeImage(
    modifier: Modifier = Modifier, url: String, height: Dp? = null
) {
    AsyncImage(
        model = url,
        contentDescription = "Recipe Image",
        contentScale = ContentScale.Crop,
        modifier = if (height == null) modifier.fillMaxWidth() else modifier.height(height)
    )
}

@Composable
private fun RecipeInformation(
    modifier: Modifier = Modifier,
    title: String,
    socialRank: Float,
    ingredients: List<String>?,
    isIngredientsVisible: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(
                vertical = VerticalPadding, horizontal = HorizontalPadding
            )
    ) {
        RecipeHeader(title = title, socialRank = socialRank)
        RecipeIngredients(ingredients = ingredients, isVisible = isIngredientsVisible)
    }
}

@Composable
private fun RecipeHeader(title: String, socialRank: Float) {
    val fontWeight = FontWeight.Bold
    val titleLarge = MaterialTheme.typography.titleLarge.copy(fontWeight = fontWeight)
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = titleLarge, modifier = Modifier.weight(1f))
        Text("$socialRank", style = titleLarge.copy(color = DarkGreen))
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
private fun RecipeIngredients(ingredients: List<String>?, isVisible: Boolean) {
    val duration = 1000
    val offsetY by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 50.dp,
        label = "Translate Animation",
        animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing),
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = duration), label = "Opacity Animation"
    )

    LazyColumn(
        modifier = Modifier
            .offset(y = offsetY)
            .graphicsLayer(alpha = alpha)
    ) {
        item {
            Text(
                "Ingredients",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = IngredientsHeaderBottomPadding)

            )
        }
        ingredients?.let {
            items(it.size) { i ->
                Text(
                    ingredients[i],
                    modifier = Modifier.padding(vertical = IngredientVerticalPadding)
                )
            }
        }
    }
}
