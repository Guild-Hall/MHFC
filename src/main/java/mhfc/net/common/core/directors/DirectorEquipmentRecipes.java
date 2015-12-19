package mhfc.net.common.core.directors;

import mhfc.net.common.core.builders.BuilderEquipmentRecipe;
import mhfc.net.common.core.data.EquipmentRecipeRegistryData;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.item.materials.*;
import mhfc.net.common.item.materials.ItemRathalos.RathalosSubType;
import mhfc.net.common.item.materials.ItemSac.SacSubType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DirectorEquipmentRecipes {

	// Armors
	public final EquipmentRecipe recipe_tigrex_helm;
	public final EquipmentRecipe recipe_tigrex_chest;
	public final EquipmentRecipe recipe_tigrex_legs;
	public final EquipmentRecipe recipe_tigrex_boots;
	public final EquipmentRecipe recipe_kirin_helm;
	// Weapon
	public final EquipmentRecipe recipe_gs_rathalos_firesword;
	public final EquipmentRecipe recipe_gs_kirin_thundersword;
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

	public void construct(EquipmentRecipeRegistryData dataObject) {
		dataObject.register(recipe_tigrex_helm);
		dataObject.register(recipe_tigrex_chest);
		dataObject.register(recipe_tigrex_legs);
		dataObject.register(recipe_tigrex_boots);
		dataObject.register(recipe_kirin_helm);
		// Weapon
		dataObject.register(recipe_gs_rathalos_firesword);
		dataObject.register(recipe_gs_kirin_thundersword);
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
	}

	public DirectorEquipmentRecipes() {

		BuilderEquipmentRecipe builder = new BuilderEquipmentRecipe();

		builder.setProduct(new ItemStack(MHFCItemRegistry.armor_tigrex_helm));
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.SKULLSHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 2,
			ItemTigrex.TigrexSubType.SHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 2,
			ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		builder.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_helm = builder.build();

		builder.setProduct(new ItemStack(MHFCItemRegistry.armor_tigrex_chest));
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 2,
			ItemTigrex.TigrexSubType.FANG.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.CLAW.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.SHELL.ordinal());
		builder.setRequiredHeat(200).setDuration(600);
		recipe_tigrex_chest = builder.build();

		builder.setProduct(new ItemStack(MHFCItemRegistry.armor_tigrex_legs));
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.CLAW.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 2,
			ItemTigrex.TigrexSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 2,
			ItemTigrex.TigrexSubType.FANG.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 5,
			ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		builder.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_legs = builder.build();

		builder.setProduct(new ItemStack(MHFCItemRegistry.armor_tigrex_boots));
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.FANG.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 2,
			ItemTigrex.TigrexSubType.SHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 2,
			ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		builder.setRequiredHeat(200).setDuration(300);
		recipe_tigrex_boots = builder.build();

		builder.setProduct(new ItemStack(MHFCItemRegistry.armor_kirin_helm));
		for (int a = 0; a < 2; a++) {
			builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 4,
				ItemKirin.KirinSubType.MANE.ordinal());
		}
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 4,
			ItemKirin.KirinSubType.THUNDERTAIL.ordinal());
		builder.setRequiredHeat(500).setDuration(300);
		recipe_kirin_helm = builder.build();

		/*
		 * @Andreas: Im sorry to adjust the formating code here but i can
		 * understand this a little bit more. Thanks -Heltrato. *
		 */

		// GreatSword

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_gs_bone));
		builder.addIngredient(Item.getItemById(352), 6, 0);
		builder.addIngredient(MHFCItemRegistry.mhfcitemlumberbar, 4, 0);
		builder.addIngredient(MHFCItemRegistry.mhfcitemmoldediron, 2, 0);
		builder.setRequiredHeat(100).setDuration(300);
		recipe_gs_bone = builder.build();

		// dont mind//

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_gs_rathalosfire));
		builder.addIngredient(MHFCItemRegistry.mhfcitemrathalos, 2,
			RathalosSubType.WING.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemsac, 2, SacSubType.FIRE
			.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemrathalos, 5,
			RathalosSubType.MARROW.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemfirestone, 3, 0);
		builder.setRequiredHeat(300).setDuration(900);
		recipe_gs_rathalos_firesword = builder.build();

		//

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_gs_kirinthunders));
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 7,
			ItemKirin.KirinSubType.MANE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 5,
			ItemIngot.IngotsSubType.CARBALITE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 4,
			ItemKirin.KirinSubType.LIGHTCRYSTAL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 2,
			ItemKirin.KirinSubType.PURECRYSTAL.ordinal());
		builder.setRequiredHeat(300).setDuration(900);
		recipe_gs_kirin_thundersword = builder.build();

		//

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_gs_tigrex));
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 6,
			ItemTigrex.TigrexSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 7,
			ItemTigrex.TigrexSubType.FANG.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.SHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 4,
			ItemTigrex.TigrexSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 1,
			ItemTigrex.TigrexSubType.SKULLSHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 2,
			ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		builder.setRequiredHeat(500).setDuration(1000);
		recipe_gs_agito = builder.build();

		//

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_gs_berserkers));
		builder.addIngredient(MHFCItemRegistry.mhfcitemdeviljho, 7,
			ItemDeviljho.DeviljhoSubType.FANG.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemdeviljho, 7,
			ItemDeviljho.DeviljhoSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemdeviljho, 5,
			ItemDeviljho.DeviljhoSubType.TALON.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemdeviljho, 3,
			ItemDeviljho.DeviljhoSubType.SCALP.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 5,
			ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		builder.setRequiredHeat(600).setDuration(1500);
		recipe_gs_berserker = builder.build();

		//

		// Hammer Class

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_hm_warhammer));
		builder.addIngredient(Item.getItemById(265), 7, 0);// 265 is iron
															// ingot
		builder.addIngredient(Item.getItemFromBlock(
			MHFCBlockRegistry.mhfcblockdiskstone), 2, 0);
		builder.setRequiredHeat(100).setDuration(200);
		builder.addIngredient(MHFCItemRegistry.mhfcitemsteelbar, 4, 0);
		recipe_hm_war = builder.build();

		//

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_hm_warhammerplus));
		builder.addIngredient(MHFCItemRegistry.weapon_hm_warhammer, 1, 0);
		builder.addIngredient(MHFCItemRegistry.mhfcitemsteelbar, 2, 0);
		builder.addIngredient(MHFCItemRegistry.mhfcitemmoldediron, 1, 0);
		builder.addIngredient(Item.getItemById(265), 5, 0);// 265 is iron
		builder.setRequiredHeat(100).setDuration(250);
		recipe_hm_warplus = builder.build();

		//

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_hm_warslammer));
		builder.addIngredient(MHFCItemRegistry.weapon_hm_warhammerplus, 1, 0);
		builder.addIngredient(MHFCItemRegistry.mhfcitemsteelbar, 7, 0);
		builder.addIngredient(MHFCItemRegistry.mhfcitemmoldediron, 3, 0);
		builder.setRequiredHeat(150).setDuration(300);
		recipe_hm_warslammer = builder.build();

		//

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_hm_rathalos));
		builder.addIngredient(MHFCItemRegistry.mhfcitemrathalos, 8,
			ItemRathalos.RathalosSubType.SHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemrathalos, 2,
			ItemRathalos.RathalosSubType.MARROW.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemfirestone, 1, 0);
		builder.setRequiredHeat(300).setDuration(500);
		recipe_hm_rathalos = builder.build();

		//

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_hm_tigrex));
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 3,
			ItemTigrex.TigrexSubType.CLAW.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 5,
			ItemTigrex.TigrexSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 5,
			ItemTigrex.TigrexSubType.SHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.MHFCItemWyvernCoin, 10, 0);
		builder.setRequiredHeat(400).setDuration(700);
		recipe_hm_tigrex = builder.build();

		//

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_hm_kirinspark));
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 10,
			ItemKirin.KirinSubType.MANE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 6,
			ItemKirin.KirinSubType.PLATINUMMANE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 5,
			ItemKirin.KirinSubType.LIGHTCRYSTAL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 3,
			ItemKirin.KirinSubType.PURECRYSTAL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemkirin, 1,
			ItemKirin.KirinSubType.THUNDERTAIL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 4,
			ItemIngot.IngotsSubType.ELTALITE.ordinal());
		builder.setRequiredHeat(750).setDuration(1200);
		recipe_hm_kirin = builder.build();;

		//

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_hm_devilsdue));
		builder.addIngredient(MHFCItemRegistry.mhfcitemdeviljho, 10,
			ItemDeviljho.DeviljhoSubType.FANG.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemdeviljho, 4,
			ItemDeviljho.DeviljhoSubType.SCALE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemdeviljho, 6,
			ItemDeviljho.DeviljhoSubType.SCALP.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 4,
			ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		builder.setRequiredHeat(750).setDuration(1800);
		recipe_hm_devilsdue = builder.build();

		//
		// Longsword class

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_ls_ironkatana));
		builder.addIngredient(MHFCItemRegistry.mhfcitemsteelbar, 7, 0);
		builder.addIngredient(MHFCItemRegistry.mhfcitemlumberbar, 2, 0);
		builder.setRequiredHeat(150).setDuration(600);
		recipe_ls_ironkatana = builder.build();

		//

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_ls_darkvipern));
		builder.addIngredient(MHFCItemRegistry.mhfcitemremobra, 6,
			ItemRemobra.RemobraSubType.SKIN.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemremobra, 6,
			ItemRemobra.RemobraSubType.STRIPE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemremobra, 2,
			ItemRemobra.RemobraSubType.WING.ordinal());
		builder.setRequiredHeat(500).setDuration(750);
		recipe_ls_darkvipern = builder.build();

		//
		// Hunting Horn Class

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_hh_ivoryhorn));
		builder.setRequiredHeat(100).setDuration(250);
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 3,
			ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemlumberbar, 1, 0);
		builder.addIngredient(Item.getItemById(352), 6, 0);
		recipe_hh_ivoryhorn = builder.build();

		builder.setProduct(new ItemStack(
			MHFCItemRegistry.weapon_hh_metalbagpipe));
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 3,
			ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemmoldediron, 2, 0);
		builder.setRequiredHeat(200).setDuration(400);
		recipe_hh_metalbagpipe = builder.build();

		builder.setProduct(new ItemStack(MHFCItemRegistry.weapon_hh_tigrex));
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 5,
			ItemTigrex.TigrexSubType.SHELL.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemtigrex, 7,
			ItemTigrex.TigrexSubType.FANG.ordinal());
		builder.addIngredient(MHFCItemRegistry.mhfcitemingot, 10,
			ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		builder.setRequiredHeat(350).setDuration(600);
		recipe_hh_tigrex = builder.build();

	}

}
