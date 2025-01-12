package com.recipes.app_view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.recipes.app_view.RecipeId
import com.recipes.app_view.adapters.OnRecipeListener
import com.recipes.app_view.adapters.RecipeRecyclerAdapter
import com.recipes.app_view.databinding.FragmentRecipeSearchBinding
import com.recipes.app_view.uistate.RecipeSearchUiState
import com.recipes.app_view.viewmodels.RecipeSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeSearchFragment : Fragment(), OnRecipeListener {

    companion object {
        private const val TAG = "RecipeSearchFragment"
    }

    private var _binding: FragmentRecipeSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerAdapter: RecipeRecyclerAdapter

    private val viewModel: RecipeSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView()
        initRecyclerView()
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSearchView() {
        binding.searchView.apply {
            setIconifiedByDefault(false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val text = newText ?: ""
                    if (text.isBlank())
                        viewModel.deleteSearchText()
                    else
                        viewModel.searchRecipes(text)
                    return true
                }
            })
        }

    }

    private fun initRecyclerView() {
        recyclerAdapter = RecipeRecyclerAdapter(this)
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                updateUi(state)
            }
        }
    }

    private fun updateUi(state: RecipeSearchUiState) {
        when (state) {
            is RecipeSearchUiState.Empty -> {
                binding.apply {
                    loadingIndicator.isVisible = false
                    recyclerView.isVisible = false
                    errorMessage.isVisible = false
                }
            }

            is RecipeSearchUiState.Loading -> {
                binding.apply {
                    loadingIndicator.isVisible = true
                    recyclerView.isVisible = false
                    errorMessage.isVisible = false
                }
            }

            is RecipeSearchUiState.Success -> {
                binding.apply {
                    loadingIndicator.isVisible = false
                    errorMessage.isVisible = false
                    recyclerView.isVisible = true
                    recyclerAdapter.setRecipes(state.recipes)
                }
            }

            is RecipeSearchUiState.Error -> {
                binding.apply {
                    loadingIndicator.isVisible = false
                    errorMessage.isVisible = true
                    errorMessage.text = state.message
                    recyclerView.isVisible = false
                }
            }
        }
    }

    override fun onRecipeClick(recipeId: String, title: String) {
        findNavController().navigate(RecipeId(recipeId, title = title))
    }
}