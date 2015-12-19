package mhfc.net.common.crafting.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MHFCShapelessRecipe implements IRecipe {
	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;

	/** Is a List of ItemStack that composes the recipe. */
	public final List<ItemStack> recipeItems;

	public MHFCShapelessRecipe(ItemStack par1ItemStack,
		List<ItemStack> par2List) {
		this.recipeOutput = par1ItemStack.copy();
		this.recipeItems = new ArrayList<ItemStack>(par2List);
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting par1InventoryCrafting,
		World par2World) {
		List<ItemStack> arraylist = new ArrayList<ItemStack>(this.recipeItems);

		for (int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
			ItemStack itemstack = par1InventoryCrafting.getStackInSlot(i);

			if (itemstack != null) {
				boolean flag = false;
				Iterator<ItemStack> iterator = arraylist.iterator();

				while (iterator.hasNext()) {
					ItemStack itemstack1 = iterator.next();

					if (itemstack1.getItemDamage() == 32767 || itemstack
						.getItemDamage() == itemstack1.getItemDamage()) {
						flag = true;
						arraylist.remove(itemstack1);
						break;
					}
				}

				if (!flag) {
					return false;
				}
			}
		}

		return arraylist.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(
		InventoryCrafting par1InventoryCrafting) {
		return this.recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return this.recipeItems.size();
	}
}
