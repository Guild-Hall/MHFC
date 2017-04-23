package mhfc.net.common.crafting.recipes.equipment;

import java.util.List;

import mhfc.net.common.crafting.recipes.MHFCShapelessRecipe;
import mhfc.net.common.item.ItemType;
import net.minecraft.item.ItemStack;

public class EquipmentRecipe extends MHFCShapelessRecipe {

	public static enum RecipeType {
		ARMOR,
		WEAPON,
		UPGRADE,
		MHFC;

		public static RecipeType getDefaultType(ItemType itemType) {
			switch (itemType.getGeneralType()) {
			case ARMOR :
				return ARMOR;
			case WEAPON :
				return WEAPON;
			default :
				return MHFC;
			}
		}
	}

	private final int heat, duration;
	private final RecipeType recipeType;

	public static RecipeType getDefaultRecipeType(ItemStack output) {
		return RecipeType.getDefaultType(ItemType.getTypeOf(output));
	}

	public EquipmentRecipe(ItemStack recipeProduct,
			List<ItemStack> recipeIngredients, int requiredHeat, int duration) {
		this(getDefaultRecipeType(recipeProduct), recipeProduct,
				recipeIngredients, requiredHeat, duration);
	}

	public EquipmentRecipe(RecipeType type, ItemStack recipeProduct,
			List<ItemStack> recipeIngredients, int requiredHeat, int duration) {
		super(recipeProduct, recipeIngredients);
		this.recipeType = type;
		this.heat = requiredHeat;
		this.duration = duration;

	}

	public void fillRequirements(ItemStack[] ingredientSlots) {
		int length = Math.min(ingredientSlots.length, recipeItems.size());
		for (int i = 0; i < length; i++) {
			ingredientSlots[i] = recipeItems.get(i);
		}
	}

	public int getRequiredHeat() {
		return heat;
	}

	public int getDuration() {
		return duration;
	}

	public ItemType getOutputType() {
		ItemStack it = getRecipeOutput();
		return ItemType.getTypeOf(it);
	}

	public RecipeType getRecipeType() {
		return recipeType;
	}

}
