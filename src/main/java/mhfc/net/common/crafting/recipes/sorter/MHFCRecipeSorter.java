package mhfc.net.common.crafting.recipes.sorter;

import java.util.Comparator;

import mhfc.net.common.crafting.MHFCCraftingManager;
import mhfc.net.common.crafting.recipes.MHFCShapedRecipes;
import mhfc.net.common.crafting.recipes.MHFCShapelessRecipe;
import net.minecraft.item.crafting.IRecipe;

public class MHFCRecipeSorter implements Comparator<IRecipe> {

	final MHFCCraftingManager BigCraftingManager;

	MHFCRecipeSorter(MHFCCraftingManager par1BigCraftingManager) {
		this.BigCraftingManager = par1BigCraftingManager;
	}

	public int compareRecipes(IRecipe par1IRecipe, IRecipe par2IRecipe) {
		return par1IRecipe instanceof MHFCShapelessRecipe
				&& par2IRecipe instanceof MHFCShapedRecipes
				? 1
				: (par2IRecipe instanceof MHFCShapelessRecipe
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
