package mhfc.net.common.crafting.recipes.equipment;

import java.util.List;

import mhfc.net.common.core.registry.MHFCRegEquipmentRecipe;
import mhfc.net.common.crafting.recipes.MHFCShapelessRecipe;
import net.minecraft.item.ItemStack;

public class EquipmentRecipe extends MHFCShapelessRecipe {

	private int heat, duration;

	public EquipmentRecipe(ItemStack recipeProduct,
			List<ItemStack> recipeIngredients, int requiredHeat, int duration) {
		super(recipeProduct, recipeIngredients);
		this.heat = requiredHeat;
		this.duration = duration;
		int type = MHFCRegEquipmentRecipe.getType(this);
		MHFCRegEquipmentRecipe.register(this, type);
		System.out.println("Registered" + type + " "
				+ (MHFCRegEquipmentRecipe.getIDFor(this, type) >= 0));
	}

	public ItemStack[] getRequirements(int padTo) {
		ItemStack[] stacks = new ItemStack[padTo];
		return recipeItems.toArray(stacks);
	}

	public int getRequiredHeat() {
		return heat;
	}

	public int getDuration() {
		return duration;
	}

}
