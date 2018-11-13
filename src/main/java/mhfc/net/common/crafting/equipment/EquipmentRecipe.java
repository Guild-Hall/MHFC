package mhfc.net.common.crafting.equipment;

import mhfc.net.common.crafting.MHFCShapelessRecipe;
import mhfc.net.common.item.ItemType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class EquipmentRecipe extends MHFCShapelessRecipe {

	public static enum RecipeType {
		ARMOR,
		WEAPON,
		UPGRADE,
		MHFC;

		public static RecipeType getDefaultType(ItemType itemType) {
			switch (itemType.getGeneralType()) {
			case ARMOR:
				return ARMOR;
			case WEAPON:
				return WEAPON;
			case NONE:
			default:
				return MHFC;
			}
		}
	}

	private final int heat, duration;
	private final RecipeType recipeType;

	public static RecipeType getDefaultRecipeType(ItemStack output) {
		return RecipeType.getDefaultType(ItemType.getTypeOf(output));
	}

	public EquipmentRecipe(ItemStack recipeProduct, NonNullList<Ingredient> recipeIngredients, int requiredHeat, int duration) {
		this(getDefaultRecipeType(recipeProduct), recipeProduct, recipeIngredients, requiredHeat, duration);
	}

	public EquipmentRecipe(
			RecipeType type,
			ItemStack recipeProduct,
			NonNullList<Ingredient> recipeIngredients,
			int requiredHeat,
			int duration) {
		super("group", recipeProduct, recipeIngredients);
		this.recipeType = type;
		this.heat = requiredHeat;
		this.duration = duration;

	}


	public void fillRequirements(Ingredient[] ingredientSlots) {
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
