package mhfc.net.common.crafting.recipes.equipment;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class EquipmentRecipeRegistry {
	public static int TYPE_ARMOR_HEAD = 0;
	public static int TYPE_ARMOR_BODY = 1;
	public static int TYPE_ARMOR_PANTS = 2;
	public static int TYPE_ARMOR_BOOTS = 3;
	public static int TYPE_WEAPON_GREAT_SWORD = 4;
	public static int TYPE_WEAPON_LONG_SWORD = 5;
	public static int TYPE_WEAPON_HAMMER = 6;
	public static int TYPE_WEAPON_HUNTING_HORN = 7;
	public static int TYPE_WEAPON_SWORD_AND_SHIELD = 8;
	public static int TYPE_WEAPON_DOUBLE_SWORD = 9;
	public static int TYPE_WEAPON_LANCE = 10;
	public static int TYPE_WEAPON_GUNLANCE = 11;
	public static int TYPE_WEAPON_BOW = 12;
	public static int TYPE_WEAPON_SMALL_BOWGUN = 13;
	public static int TYPE_WEAPON_BIG_BOWGUN = 14;

	private static Map<Integer, Set<EquipmentRecipe>> mapOfRecipeSets = new HashMap<Integer, Set<EquipmentRecipe>>();
	static {
		for (int i = 0; i < 15; i++) {
			mapOfRecipeSets.put(new Integer(i),
					new LinkedHashSet<EquipmentRecipe>());
		}
	}

	public static Set<EquipmentRecipe> getRecipesFor(int type) {
		return mapOfRecipeSets.get(new Integer(type));
	}

	public static void register(EquipmentRecipe recipe, int type) {
		if (type < 0 | type > 14)
			return;
		mapOfRecipeSets.get(new Integer(type)).add(recipe);
	}
}
