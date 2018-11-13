package mhfc.net.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

public class MHFCShapelessRecipe extends ShapelessRecipes {

	public MHFCShapelessRecipe(String group, ItemStack output, NonNullList<Ingredient> ingredients) {
		super(group, output, ingredients);
	}


}
