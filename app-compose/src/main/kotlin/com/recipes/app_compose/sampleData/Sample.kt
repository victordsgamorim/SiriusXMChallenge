package com.recipes.recipesdk.models

fun getSampleRecipes(): List<Recipe> {
    return List(25) { index ->
        Recipe(
            recipeId = "1",
            title = "Spaghetti Bolognese",
            publisher = "Italian Recipes",
            publisherUrl = "https://italianrecipes.com",
            sourceUrl = "https://italianrecipes.com/spaghetti-bolognese",
            imageUrl = "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
            socialRank = 9.5f,
            ingredients = listOf(
                "Spaghetti",
                "Ground beef",
                "Tomato sauce",
                "Onion",
                "Garlic",
                "Olive oil",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
                "Parmesan cheese",
            )
        )
    }
}
