package mhfc.net.common.core.directors;

import mhfc.net.common.core.builders.BuilderEquipmentRecipe;
import mhfc.net.common.core.data.EquipmentRecipeRegistryData;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.item.materials.ItemDeviljho;
import mhfc.net.common.item.materials.ItemIngot;
import mhfc.net.common.item.materials.ItemKirin;
import mhfc.net.common.item.materials.ItemRathalos;
import mhfc.net.common.item.materials.ItemRathalos.RathalosSubType;
import mhfc.net.common.item.materials.ItemRemobra;
import mhfc.net.common.item.materials.ItemSac.SacSubType;
import mhfc.net.common.item.materials.ItemTigrex;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DirectorEquipmentRecipes {

	// Armors
	public final EquipmentRecipe recipe_tigrex_helm;
	public final EquipmentRecipe recipe_tigrex_chest;
	public final EquipmentRecipe recipe_tigrex_legs;
	public final EquipmentRecipe recipe_tigrex_boots;

	public final EquipmentRecipe recipe_yukumo_helm;
	public final EquipmentRecipe recipe_yukumo_chest;
	public final EquipmentRecipe recipe_yukumo_legs;
	public final EquipmentRecipe recipe_yukumo_boots;

	public final EquipmentRecipe recipe_vangis_helm;
	public final EquipmentRecipe recipe_vangis_chest;
	public final EquipmentRecipe recipe_vangis_legs;
	public final EquipmentRecipe recipe_vangis_boots;

	public final EquipmentRecipe recipe_rathalos_helm;
	public final EquipmentRecipe recipe_rathalos_chest;
	public final EquipmentRecipe recipe_rathalos_legs;
	public final EquipmentRecipe recipe_rathalos_boots;

	public final EquipmentRecipe recipe_kirin_helm;
	// Weapon
	public final EquipmentRecipe recipe_gs_redwing;
	public final EquipmentRecipe recipe_gs_thundersword;
	public final EquipmentRecipe recipe_gs_berserker;
	public final EquipmentRecipe recipe_gs_agito;
	public final EquipmentRecipe recipe_gs_bone;

	public final EquipmentRecipe recipe_hm_war;
	public final EquipmentRecipe recipe_hm_warplus;
	public final EquipmentRecipe recipe_hm_warslammer;
	public final EquipmentRecipe recipe_hm_tigrex;
	public final EquipmentRecipe recipe_hm_kirin;
	public final EquipmentRecipe recipe_hm_rathalos;
	public final EquipmentRecipe recipe_hm_devilsdue;

	public final EquipmentRecipe recipe_ls_ironkatana;
	public final EquipmentRecipe recipe_ls_darkvipern;

	public final EquipmentRecipe recipe_hh_ivoryhorn;
	public final EquipmentRecipe recipe_hh_metalbagpipe;
	public final EquipmentRecipe recipe_hh_tigrex;

	public final EquipmentRecipe recipe_b_hunters;
	public final EquipmentRecipe recipe_b_huntersstout;

	public void construct(EquipmentRecipeRegistryData dataObject) {

		dataObject.register(recipe_yukumo_helm);
		dataObject.register(recipe_rathalos_helm);
		dataObject.register(recipe_tigrex_helm);
		dataObject.register(recipe_kirin_helm);
		dataObject.register(recipe_vangis_helm);

		dataObject.register(recipe_yukumo_chest);
		dataObject.register(recipe_rathalos_chest);
		dataObject.register(recipe_tigrex_chest);
		dataObject.register(recipe_vangis_chest);

		dataObject.register(recipe_yukumo_legs);
		dataObject.register(recipe_rathalos_legs);
		dataObject.register(recipe_tigrex_legs);
		dataObject.register(recipe_vangis_legs);

		dataObject.register(recipe_yukumo_boots);
		dataObject.register(recipe_rathalos_boots);
		dataObject.register(recipe_tigrex_boots);
		dataObject.register(recipe_vangis_boots);

		// Weapon
		dataObject.register(recipe_gs_redwing);
		dataObject.register(recipe_gs_thundersword);
		dataObject.register(recipe_gs_berserker);
		dataObject.register(recipe_gs_agito);
		dataObject.register(recipe_gs_bone);
		dataObject.register(recipe_hm_war);
		dataObject.register(recipe_hm_warplus);
		dataObject.register(recipe_hm_warslammer);
		dataObject.register(recipe_hm_tigrex);
		dataObject.register(recipe_hm_kirin);
		dataObject.register(recipe_hm_rathalos);
		dataObject.register(recipe_hm_devilsdue);
		dataObject.register(recipe_ls_ironkatana);
		dataObject.register(recipe_ls_darkvipern);
		dataObject.register(recipe_hh_ivoryhorn);
		dataObject.register(recipe_hh_metalbagpipe);
		dataObject.register(recipe_hh_tigrex);

		dataObject.register(recipe_b_hunters);
		dataObject.register(recipe_b_huntersstout);
	}

	public DirectorEquipmentRecipes() {

		BuilderEquipmentRecipe builder = new BuilderEquipmentRecipe();
		MHFCItemRegistry itemRegistry = MHFCItemRegistry.getRegistry();

		builder.setProduct(new ItemStack(itemRegistry.armor_tigrex_helm));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SKULLSHELL, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 2));
		builder.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_helm = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_tigrex_chest));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.CLAW, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 3));
		builder.setRequiredHeat(200).setDuration(600);
		recipe_tigrex_chest = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_tigrex_legs));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.CLAW, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 5));
		builder.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_legs = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_tigrex_boots));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2));
		builder.setRequiredHeat(200).setDuration(300);
		recipe_tigrex_boots = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_yukumo_helm));
		builder.addIngredient(itemRegistry.lumberbar, 2, 0);
		builder.addIngredient(Item.getItemById(265), 11, 0);
		builder.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_helm = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_yukumo_chest));
		builder.addIngredient(itemRegistry.lumberbar, 10, 0);
		builder.addIngredient(Item.getItemById(265), 15, 0);
		builder.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_chest = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_yukumo_legs));
		builder.addIngredient(itemRegistry.lumberbar, 10, 0);
		builder.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_legs = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_yukumo_boots));
		builder.addIngredient(itemRegistry.lumberbar, 4, 0);
		builder.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_boots = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_vangis_helm));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.HIDE, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2));// Will be replaced by Conquerors Seal
		builder.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_helm = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_vangis_chest));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.HIDE, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRathalos.RathalosSubType.WEBBING, 8));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.CARBALITE, 4));// Will be Replaced by Dragonbone Relic
		builder.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_chest = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_vangis_legs));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 12));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TAIL, 1)); // Will be replaced by Gem
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 4)); // Will be replaced by Rath Medul
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.CARBALITE, 4)); //Will be replaced by Dragonbone Relic
		builder.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_legs = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_vangis_boots));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TALON, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TAIL, 1)); //gem
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2)); //dragonbonerelic
		builder.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_boots = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_rathalos_helm));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.SHELL, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.PLATE, 4)); // will be replaced by scale
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 4));
		builder.addIngredient(itemRegistry.firestone, 4, 0);
		builder.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_helm = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_rathalos_chest));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.SHELL, 4));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.PLATE, 4)); // will be replaced by scale
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.MARROW, 1));
		builder.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_chest = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_rathalos_legs));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WEBBING, 4));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WING, 1)); // will be replaced by TAIL
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.SHELL, 4));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 3));
		builder.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_legs = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_rathalos_boots));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.PLATE, 4)); // will be replaced by scale
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WEBBING, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.MARROW, 1));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WEBBING, 1));// will be repalced by rare scarab
		builder.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_boots = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.armor_kirin_helm));
		for (int a = 0; a < 2; a++) {
			builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.MANE, 4));
		}
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.THUNDERTAIL, 4));
		builder.setRequiredHeat(500).setDuration(300);
		recipe_kirin_helm = builder.build();

		/*
		 * @Andreas: Im sorry to adjust the formating code here but i can
		 * understand this a little bit more. Thanks -Heltrato. *
		 */

		// GreatSword

		builder.setProduct(new ItemStack(itemRegistry.weapon_gs_bone));
		builder.addIngredient(Item.getItemById(352), 6, 0);
		builder.addIngredient(itemRegistry.lumberbar, 4, 0);
		builder.addIngredient(itemRegistry.moldedIron, 2, 0);
		builder.setRequiredHeat(100).setDuration(300);
		recipe_gs_bone = builder.build();

		// dont mind//

		builder.setProduct(new ItemStack(itemRegistry.weapon_gs_rathalosfire));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WING, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(SacSubType.FIRE, 2));
		builder.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.MARROW, 5));
		builder.addIngredient(itemRegistry.firestone, 3, 0);
		builder.setRequiredHeat(300).setDuration(900);
		recipe_gs_redwing = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_gs_kirinthunders));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.MANE, 7));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.CARBALITE, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.LIGHTCRYSTAL, 4));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.PURECRYSTAL, 2));
		builder.setRequiredHeat(300).setDuration(900);
		recipe_gs_thundersword = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_gs_tigrex));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 6));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 7));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 4));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SKULLSHELL, 1));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2));
		builder.setRequiredHeat(500).setDuration(1000);
		recipe_gs_agito = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_gs_berserkers));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 7));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 7));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TALON, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 5));
		builder.setRequiredHeat(600).setDuration(1500);
		recipe_gs_berserker = builder.build();

		//

		// Hammer Class

		builder.setProduct(new ItemStack(itemRegistry.weapon_hm_warhammer));
		builder.addIngredient(Item.getItemById(265), 7, 0);// 265 is iron
															// ingot
		builder.setRequiredHeat(100).setDuration(200);
		builder.addIngredient(itemRegistry.steelbar, 4, 0);
		recipe_hm_war = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_hm_warhammerplus));
		builder.addIngredient(itemRegistry.weapon_hm_warhammer, 1, 0);
		builder.addIngredient(itemRegistry.steelbar, 2, 0);
		builder.addIngredient(itemRegistry.moldedIron, 1, 0);
		builder.addIngredient(Item.getItemById(265), 5, 0);// 265 is iron
		builder.setRequiredHeat(100).setDuration(250);
		recipe_hm_warplus = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_hm_warslammer));
		builder.addIngredient(itemRegistry.weapon_hm_warhammerplus, 1, 0);
		builder.addIngredient(itemRegistry.steelbar, 7, 0);
		builder.addIngredient(itemRegistry.moldedIron, 3, 0);
		builder.setRequiredHeat(150).setDuration(300);
		recipe_hm_warslammer = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_hm_rathalos));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRathalos.RathalosSubType.SHELL, 8));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRathalos.RathalosSubType.MARROW, 2));
		builder.addIngredient(itemRegistry.firestone, 1, 0);
		builder.setRequiredHeat(300).setDuration(500);
		recipe_hm_rathalos = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_hm_tigrex));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.CLAW, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 5));
		builder.addIngredient(itemRegistry.wyvernCoin, 10, 0);
		builder.setRequiredHeat(400).setDuration(700);
		recipe_hm_tigrex = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_hm_kirinspark));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.MANE, 10));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.PLATINUMMANE, 6));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.LIGHTCRYSTAL, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.PURECRYSTAL, 3));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.THUNDERTAIL, 1));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.ELTALITE, 4));
		builder.setRequiredHeat(750).setDuration(1200);
		recipe_hm_kirin = builder.build();
		;

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_hm_devilsdue));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 10));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 4));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 6));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 4));
		builder.setRequiredHeat(750).setDuration(1800);
		recipe_hm_devilsdue = builder.build();

		//
		// Longsword class

		builder.setProduct(new ItemStack(itemRegistry.weapon_ls_ironkatana));
		builder.addIngredient(itemRegistry.steelbar, 7, 0);
		builder.addIngredient(itemRegistry.lumberbar, 2, 0);
		builder.setRequiredHeat(150).setDuration(600);
		recipe_ls_ironkatana = builder.build();

		//

		builder.setProduct(new ItemStack(itemRegistry.weapon_ls_darkvipern));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRemobra.RemobraSubType.SKIN, 6));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRemobra.RemobraSubType.STRIPE, 6));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRemobra.RemobraSubType.WING, 2));
		builder.setRequiredHeat(500).setDuration(750);
		recipe_ls_darkvipern = builder.build();

		//
		// Hunting Horn Class

		builder.setProduct(new ItemStack(itemRegistry.weapon_hh_ivoryhorn));
		builder.setRequiredHeat(100).setDuration(250);
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 3));
		builder.addIngredient(itemRegistry.lumberbar, 1, 0);
		builder.addIngredient(Item.getItemById(352), 6, 0);
		recipe_hh_ivoryhorn = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.weapon_hh_metalbagpipe));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 3));
		builder.addIngredient(itemRegistry.moldedIron, 2, 0);
		builder.setRequiredHeat(200).setDuration(400);
		recipe_hh_metalbagpipe = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.weapon_hh_tigrex));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 5));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 7));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 10));
		builder.setRequiredHeat(350).setDuration(600);
		recipe_hh_tigrex = builder.build();

		//Bow Class

		builder.setProduct(new ItemStack(itemRegistry.weapon_b_hunters));
		builder.addIngredient(itemRegistry.lumberbar, 15, 0);
		builder.addIngredient(Item.getItemById(265), 12, 0);
		builder.addIngredient(Item.getItemById(352), 12, 0);
		builder.setRequiredHeat(350).setDuration(600);
		recipe_b_hunters = builder.build();

		builder.setProduct(new ItemStack(itemRegistry.weapon_b_huntersstout));
		builder.addIngredient(itemRegistry.lumberbar, 5, 0);
		builder.addIngredient(Item.getItemById(265), 15, 0);
		builder.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 15));
		builder.setRequiredHeat(350).setDuration(600);
		recipe_b_huntersstout = builder.build();

	}

}
