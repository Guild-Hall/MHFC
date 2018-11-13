package mhfc.net.common.core.builders;

import mhfc.net.common.crafting.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.equipment.EquipmentRecipe.RecipeType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class BuilderEquipmentRecipe {

	private RecipeType type;
	private ItemStack recipeProduct;
	NonNullList<Ingredient> recipeIngredients;
	private int requiredHeat;
	private int duration;

	public BuilderEquipmentRecipe() {
		recipeIngredients = NonNullList.create();
		reset();
	}

	public BuilderEquipmentRecipe setType(RecipeType type) {
		this.type = type;
		return this;
	}

	public BuilderEquipmentRecipe setProduct(Item item, int amount, int subtype) {
		return setProduct(BuilderEquipmentRecipe.createItemStack(item, amount, subtype));
	}

	public BuilderEquipmentRecipe setProduct(ItemStack stack) {
		recipeProduct = stack;
		return this;
	}

	public BuilderEquipmentRecipe addIngredient(Item item, int amount, int subtype) {
		return addIngredient(BuilderEquipmentRecipe.createItemStack(item, amount, subtype));
	}

	public BuilderEquipmentRecipe addIngredient(ItemStack stack) {
		recipeIngredients.add(Ingredient.fromStacks(stack));
		return this;
	}

	public BuilderEquipmentRecipe setRequiredHeat(int heat) {
		requiredHeat = heat;
		return this;
	}

	public BuilderEquipmentRecipe setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public BuilderEquipmentRecipe reset() {
		recipeIngredients = NonNullList.create();
		type = null;
		recipeProduct = ItemStack.EMPTY;
		requiredHeat = 0;
		duration = 0;
		return this;
	}

	public EquipmentRecipe build() {
		EquipmentRecipe recipe;
		if (type == null) {
			recipe = new EquipmentRecipe(recipeProduct.copy(), recipeIngredients, requiredHeat, duration);
		} else {
			recipe = new EquipmentRecipe(type, recipeProduct.copy(), recipeIngredients, requiredHeat, duration);
		}
		reset();
		return recipe;
	}

	public static ItemStack createItemStack(Item item, int stackSize, int subID) {
		final ItemStack s = new ItemStack(item, stackSize);
		s.setItemDamage(subID);
		return s;
	}

}
