package mhfc.net.common.core.builders;

import java.util.ArrayList;
import java.util.List;

import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe.RecipeType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BuilderEquipmentRecipe {

	private RecipeType type;
	private ItemStack recipeProduct;
	List<ItemStack> recipeIngredients;
	private int requiredHeat;
	private int duration;

	public BuilderEquipmentRecipe() {
		recipeIngredients = new ArrayList<ItemStack>();
		reset();
	}

	public BuilderEquipmentRecipe setType(RecipeType type) {
		this.type = type;
		return this;
	}

	public BuilderEquipmentRecipe setProduct(Item item, int amount,
		int subtype) {
		return setProduct(BuilderEquipmentRecipe.createItemStack(item, amount,
			subtype));
	}

	public BuilderEquipmentRecipe setProduct(ItemStack stack) {
		recipeProduct = stack;
		return this;
	}

	public BuilderEquipmentRecipe addIngredient(Item item, int amount,
		int subtype) {
		return addIngredient(BuilderEquipmentRecipe.createItemStack(item,
			amount, subtype));
	}

	public BuilderEquipmentRecipe addIngredient(ItemStack stack) {
		recipeIngredients.add(stack);
		return this;
	}

	public BuilderEquipmentRecipe setRequiredHeat(int heat) {
		this.requiredHeat = heat;
		return this;
	}

	public BuilderEquipmentRecipe setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public BuilderEquipmentRecipe reset() {
		recipeIngredients.clear();
		type = null;
		recipeProduct = null;
		requiredHeat = 0;
		duration = 0;
		return this;
	}

	public EquipmentRecipe build() {
		EquipmentRecipe recipe;
		if (type == null) {
			recipe = new EquipmentRecipe(recipeProduct, recipeIngredients,
				requiredHeat, duration);
		} else {
			recipe = new EquipmentRecipe(type, recipeProduct, recipeIngredients,
				requiredHeat, duration);
		}
		reset();
		return recipe;
	}

	public static ItemStack createItemStack(Item item, int stackSize,
		int subID) {
		ItemStack s = new ItemStack(item, stackSize);
		s.setItemDamage(subID);
		return s;
	}

}
