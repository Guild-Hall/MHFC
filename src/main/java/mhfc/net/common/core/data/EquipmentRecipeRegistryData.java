package mhfc.net.common.core.data;

import java.util.*;

import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe.RecipeType;

public class EquipmentRecipeRegistryData {

	private Map<RecipeType, Set<EquipmentRecipe>> mapOfRecipeSets = new HashMap<RecipeType, Set<EquipmentRecipe>>();
	private Map<RecipeType, List<EquipmentRecipe>> mapOfListOfRecipes = new HashMap<RecipeType, List<EquipmentRecipe>>();

	public EquipmentRecipeRegistryData() {
		RecipeType[] types = RecipeType.values();
		for (int i = 0; i < types.length; i++) {
			mapOfRecipeSets.put(types[i], new LinkedHashSet<EquipmentRecipe>());
			mapOfListOfRecipes.put(types[i], new LinkedList<EquipmentRecipe>());

		}
	}

	private boolean register(EquipmentRecipe recipe, RecipeType type) {
		boolean inserted = mapOfRecipeSets.get(type).add(recipe);
		if (inserted) {
			mapOfListOfRecipes.get(type).add(recipe);
		}
		return inserted;
	}

	public void register(EquipmentRecipe recipe) {
		Objects.requireNonNull(recipe);
		register(recipe, recipe.getRecipeType());
	}

	public Set<EquipmentRecipe> getRecipesForType(RecipeType type) {
		return mapOfRecipeSets.get(type);
	}

	public int getIDFor(EquipmentRecipe recipe) {
		if (recipe == null)
			return -1;
		List<EquipmentRecipe> list = mapOfListOfRecipes.get(recipe
			.getRecipeType());
		return list.indexOf(recipe);
	}

	public EquipmentRecipe getRecipeFor(int id, RecipeType type) {
		List<EquipmentRecipe> list = mapOfListOfRecipes.get(type);
		if (list == null)
			return null;
		if (id < 0 || id >= list.size())
			return null;
		return list.get(id);
	}

}
