package com.recipes.app_view.ui

import IngredientRecyclerAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.recipes.app_view.R
import com.recipes.app_view.RecipeId
import com.recipes.app_view.databinding.FragmentRecipeDetailBinding
import com.recipes.app_view.uistate.RecipeDetailsUiState
import com.recipes.app_view.viewmodels.RecipeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    companion object {
        const val TAG: String = "RecipeDetailFragment"
    }

    private lateinit var ingredientAdapter: IngredientRecyclerAdapter
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        fetchRecipe()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        ingredientAdapter = IngredientRecyclerAdapter()
        binding.recipeItemIngredientList.adapter = ingredientAdapter
        binding.recipeItemIngredientList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.uiState.collect { state ->
                updateUi(state)
            }
        }
    }

    private fun updateUi(state: RecipeDetailsUiState) {
        when (state) {
            is RecipeDetailsUiState.Loading -> {
                binding.apply {
                    recipeItemImage.isVisible = false
                    recipeItemTitle.isVisible = false
                    socialRank.isVisible = false
                    recipeItemIngredient.isVisible = false
                    recipeItemIngredientList.isVisible = false
                }
            }

            is RecipeDetailsUiState.Success -> {
                binding.apply {
                    recipeItemImage.isVisible = true
                    Glide.with(requireContext()).load(state.recipe.imageUrl)
                        .into(binding.recipeItemImage)
                    recipeItemTitle.apply {
                        isVisible = true
                        text = state.recipe.title
                    }
                    socialRank.apply {
                        isVisible = true
                        text = "${state.recipe.socialRank}"
                    }
                    recipeItemIngredient.isVisible = true
                    recipeItemIngredientList.isVisible = true
                    state.recipe.ingredients?.let {
                        ingredientAdapter.setRecipes(it)
                    }

                }
            }

            is RecipeDetailsUiState.Error -> {
                val dialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.error_dialog, null)
                dialogView.findViewById<TextView>(R.id.dialog_message).text = state.message
                val dialog = AlertDialog.Builder(requireContext()).setView(dialogView)
                    .setTitle("Ops, something went wrong!").setPositiveButton("BACK") { _, _ ->
                        pop()
                    }.create()

                dialog.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun pop() {
        findNavController().popBackStack()
    }

    private fun fetchRecipe() {
        val detailRoute = findNavController().getBackStackEntry<RecipeId>().toRoute<RecipeId>()
        val recipeId: String = detailRoute.id
        viewModel.apply {
            setRecipeId(recipeId)
            getRecipeById()
        }
    }
}