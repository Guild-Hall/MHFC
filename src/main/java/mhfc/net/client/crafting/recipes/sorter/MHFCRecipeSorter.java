package mhfc.net.client.crafting.recipes.sorter;

import java.util.Comparator;

import mhfc.net.client.crafting.MHFCCraftingManager;
import mhfc.net.client.crafting.recipes.MHFCShapedRecipes;
import mhfc.net.client.crafting.recipes.MHFCShapelessRecipes;
import net.minecraft.item.crafting.IRecipe;

public class MHFCRecipeSorter implements Comparator<IRecipe> {

	final MHFCCraftingManager BigCraftingManager;

	MHFCRecipeSorter(MHFCCraftingManager par1BigCraftingManager) {
		this.BigCraftingManager = par1BigCraftingManager;
	}

	public int compareRecipes(IRecipe par1IRecipe, IRecipe par2IRecipe) {
		return par1IRecipe instanceof MHFCShapelessRecipes
				&& par2IRecipe instanceof MHFCShapedRecipes
				? 1
				: (par2IRecipe instanceof MHFCShapelessRecipes
						&& par1IRecipe instanceof MHFCShapedRecipes
						? -1
						: (par2IRecipe.getRecipeSize() < par1IRecipe
								.getRecipeSize() ? -1 : (par2IRecipe
								.getRecipeSize() > par1IRecipe.getRecipeSize()
								? 1
								: 0)));
	}

	@Override
	public int compare(IRecipe recipe1, IRecipe recipe2) {
		return this.compareRecipes(recipe1, recipe2);
	}
}
