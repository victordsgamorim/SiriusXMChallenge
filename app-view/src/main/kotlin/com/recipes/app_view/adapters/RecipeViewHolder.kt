package com.recipes.app_view.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipes.app_view.databinding.ItemRecipeListBinding
import com.recipes.recipesdk.models.Recipe

class RecipeViewHolder(
    private val itemBinding: ItemRecipeListBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipe: Recipe, onRecipeListener: OnRecipeListener) {
        itemBinding.ingredientTitle.text = recipe.title
        itemBinding.ingredientSubtitle.text = recipe.publisher

        Glide.with(itemBinding.ingredientImage.context).load(recipe.imageUrl)
            .into(itemBinding.ingredientImage)

        itemBinding.root.setOnClickListener {
            onRecipeListener.onRecipeClick(recipeId = recipe.recipeId, title = recipe.title)
        }
    }
}

