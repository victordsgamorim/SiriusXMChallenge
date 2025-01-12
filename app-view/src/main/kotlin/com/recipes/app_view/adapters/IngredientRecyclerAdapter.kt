import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipes.app_view.databinding.ItemRecipeIngredientBinding
import com.recipes.recipesdk.models.Recipe

class IngredientRecyclerAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var ingredients: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRecipeIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as IngredientViewHolder).bind(ingredients[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRecipes(ingredients: List<String>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }


    class IngredientViewHolder(private val binding: ItemRecipeIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: String) {
            binding.recipeItemIngredientLine.text = ingredient
        }
    }


}