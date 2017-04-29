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
import mhfc.net.common.item.materials.ItemSac;
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
	
	public final EquipmentRecipe recipe_gs_bone;
	public final EquipmentRecipe recipe_gs_deadlyserpentblade;
	public final EquipmentRecipe recipe_gs_redwing;
	public final EquipmentRecipe recipe_gs_thundersword;
	public final EquipmentRecipe recipe_gs_agito;
	public final EquipmentRecipe recipe_gs_berserker;
	

	public final EquipmentRecipe recipe_hm_war;
	public final EquipmentRecipe recipe_hm_warplus;
	public final EquipmentRecipe recipe_hm_warslammer;
	public final EquipmentRecipe recipe_hm_tigrex;
	public final EquipmentRecipe recipe_hm_kirin;
//	public final EquipmentRecipe recipe_hm_rathalos;
	public final EquipmentRecipe recipe_hm_devilsdue;

	public final EquipmentRecipe recipe_ls_ironkatana;
	public final EquipmentRecipe recipe_ls_ikgrace;
	public final EquipmentRecipe recipe_ls_ikgospel;
	public final EquipmentRecipe recipe_ls_eagercleaver;
	public final EquipmentRecipe recipe_ls_devilslicer;
	public final EquipmentRecipe recipe_ls_truedevilslicer;
	public final EquipmentRecipe recipe_ls_darkvipern;
	public final EquipmentRecipe recipe_ls_saber;
	public final EquipmentRecipe recipe_ls_saberliondance;
	public final EquipmentRecipe recipe_ls_saberlionking;
	public final EquipmentRecipe recipe_ls_saberlionkaiser;
	public final EquipmentRecipe recipe_ls_saberlionroar;
	public final EquipmentRecipe recipe_ls_miragefinsword;
	public final EquipmentRecipe recipe_ls_miragefinswordplus;
	public final EquipmentRecipe recipe_ls_miragephantom;

	public final EquipmentRecipe recipe_hh_ivoryhorn;
	public final EquipmentRecipe recipe_hh_metalbagpipe;
	public final EquipmentRecipe recipe_hh_tigrex;

	public final EquipmentRecipe recipe_b_hunters;
	public final EquipmentRecipe recipe_b_huntersstout;

	public void construct(EquipmentRecipeRegistryData director) {

		director.register(recipe_yukumo_helm);
		director.register(recipe_rathalos_helm);
		director.register(recipe_tigrex_helm);
		director.register(recipe_kirin_helm);
		director.register(recipe_vangis_helm);

		director.register(recipe_yukumo_chest);
		director.register(recipe_rathalos_chest);
		director.register(recipe_tigrex_chest);
		director.register(recipe_vangis_chest);

		director.register(recipe_yukumo_legs);
		director.register(recipe_rathalos_legs);
		director.register(recipe_tigrex_legs);
		director.register(recipe_vangis_legs);

		director.register(recipe_yukumo_boots);
		director.register(recipe_rathalos_boots);
		director.register(recipe_tigrex_boots);
		director.register(recipe_vangis_boots);

		// Weapon
		
		director.register(recipe_gs_bone);
		director.register(recipe_gs_deadlyserpentblade);
		director.register(recipe_gs_redwing);
		director.register(recipe_gs_thundersword);
		director.register(recipe_gs_berserker);
		director.register(recipe_gs_agito);
		
		
		
		
		director.register(recipe_hm_war);
		director.register(recipe_hm_warplus);
		director.register(recipe_hm_warslammer);
		director.register(recipe_hm_tigrex);
		director.register(recipe_hm_kirin);
	//	dataObject.register(recipe_hm_rathalos);
		director.register(recipe_hm_devilsdue);
		
		
		director.register(recipe_ls_ironkatana);
		director.register(recipe_ls_ikgrace);
		director.register(recipe_ls_ikgospel);
		director.register(recipe_ls_eagercleaver);
		director.register(recipe_ls_devilslicer);
		director.register(recipe_ls_truedevilslicer);
		director.register(recipe_ls_darkvipern);
		director.register(recipe_ls_saber);
		director.register(recipe_ls_saberliondance);
		director.register(recipe_ls_saberlionking);
		director.register(recipe_ls_saberlionkaiser);
		director.register(recipe_ls_saberlionroar);
		director.register(recipe_ls_miragefinsword);
		director.register(recipe_ls_miragefinswordplus);
		director.register(recipe_ls_miragephantom);
		director.register(recipe_ls_darkvipern);
	
		
		director.register(recipe_hh_ivoryhorn);
		director.register(recipe_hh_metalbagpipe);
		director.register(recipe_hh_tigrex);

		director.register(recipe_b_hunters);
		director.register(recipe_b_huntersstout);
	}

	public DirectorEquipmentRecipes() {

		BuilderEquipmentRecipe recipe = new BuilderEquipmentRecipe();
		MHFCItemRegistry itemRegistry = MHFCItemRegistry.getRegistry();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_helm));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SKULLSHELL, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 2));
		recipe.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_chest));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.CLAW, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 3));
		recipe.setRequiredHeat(200).setDuration(600);
		recipe_tigrex_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_legs));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.CLAW, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 5));
		recipe.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_boots));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2));
		recipe.setRequiredHeat(200).setDuration(300);
		recipe_tigrex_boots = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_helm));
		recipe.addIngredient(itemRegistry.lumberbar, 2, 0);
		recipe.addIngredient(Item.getItemById(265), 11, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_chest));
		recipe.addIngredient(itemRegistry.lumberbar, 10, 0);
		recipe.addIngredient(Item.getItemById(265), 15, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_legs));
		recipe.addIngredient(itemRegistry.lumberbar, 10, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_boots));
		recipe.addIngredient(itemRegistry.lumberbar, 4, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_boots = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_helm));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.HIDE, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2));// Will be replaced by Conquerors Seal
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_chest));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.HIDE, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemRathalos.RathalosSubType.WEBBING, 8));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.CARBALITE, 4));// Will be Replaced by Dragonbone Relic
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_legs));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 12));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TAIL, 1)); // Will be replaced by Gem
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 4)); // Will be replaced by Rath Medul
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.CARBALITE, 4)); //Will be replaced by Dragonbone Relic
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_boots));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TALON, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TAIL, 1)); //gem
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2)); //dragonbonerelic
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_boots = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_rathalos_helm));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.SHELL, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.PLATE, 4)); // will be replaced by scale
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 4));
		recipe.addIngredient(itemRegistry.firestone, 4, 0);
		recipe.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_rathalos_chest));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.SHELL, 4));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.PLATE, 4)); // will be replaced by scale
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.MARROW, 1));
		recipe.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_rathalos_legs));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WEBBING, 4));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WING, 1)); // will be replaced by TAIL
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.SHELL, 4));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 3));
		recipe.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_rathalos_boots));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.PLATE, 4)); // will be replaced by scale
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WEBBING, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.MARROW, 1));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WEBBING, 1));// will be repalced by rare scarab
		recipe.setRequiredHeat(200).setDuration(1100);
		recipe_rathalos_boots = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_kirin_helm));
		for (int a = 0; a < 2; a++) {
			recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.MANE, 4));
		}
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.THUNDERTAIL, 4));
		recipe.setRequiredHeat(500).setDuration(300);
		recipe_kirin_helm = recipe.build();

		/*
		 * @Andreas: Im sorry to adjust the formating code here but i can
		 * understand this a little bit more. Thanks -Heltrato. *
		 */

		// GreatSword

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_bone));
		recipe.addIngredient(Item.getItemById(352), 6, 0);
		recipe.addIngredient(itemRegistry.lumberbar, 4, 0);
		recipe.addIngredient(itemRegistry.moldedIron, 2, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_gs_bone = recipe.build();
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_deadlyserpentblade));
		recipe.addIngredient(Item.getItemById(352), 6, 0); // TO BE REPLACED WITH REMOBRA SKIN
		recipe.addIngredient(itemRegistry.lumberbar, 15, 0); // LIGHT CRYSTAL
		recipe.addIngredient(itemRegistry.moldedIron, 25, 0); // IOPREY FANG
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_gs_deadlyserpentblade = recipe.build();
		
		

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_rathalosfire));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.WING, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(SacSubType.FLAME, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(RathalosSubType.MARROW, 5));
		recipe.addIngredient(itemRegistry.firestone, 3, 0);
		recipe.setRequiredHeat(300).setDuration(900);
		recipe_gs_redwing = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_kirinthunders));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.MANE, 7));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.CARBALITE, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.LIGHTCRYSTAL, 4));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.PURECRYSTAL, 2));
		recipe.setRequiredHeat(300).setDuration(900);
		recipe_gs_thundersword = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_tigrex));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 6));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 7));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 4));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SKULLSHELL, 1));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 2));
		recipe.setRequiredHeat(500).setDuration(1000);
		recipe_gs_agito = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_berserkers));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 7));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 7));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.TALON, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 5));
		recipe.setRequiredHeat(600).setDuration(1500);
		recipe_gs_berserker = recipe.build();

		//

		// Hammer Class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_warhammer));
		recipe.addIngredient(Item.getItemById(265), 7, 0);// 265 is iron
															// ingot
		recipe.addIngredient(Item.getItemFromBlock(MHFCBlockRegistry.getRegistry().mhfcblockdiskstone), 2, 0);
		recipe.setRequiredHeat(100).setDuration(200);
		recipe.addIngredient(itemRegistry.steelbar, 4, 0);
		recipe_hm_war = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_warhammerplus));
		recipe.addIngredient(itemRegistry.weapon_hm_warhammer, 1, 0);
		recipe.addIngredient(itemRegistry.steelbar, 2, 0);
		recipe.addIngredient(itemRegistry.moldedIron, 1, 0);
		recipe.addIngredient(Item.getItemById(265), 5, 0);// 265 is iron
		recipe.setRequiredHeat(100).setDuration(250);
		recipe_hm_warplus = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_warslammer));
		recipe.addIngredient(itemRegistry.weapon_hm_warhammerplus, 1, 0);
		recipe.addIngredient(itemRegistry.steelbar, 7, 0);
		recipe.addIngredient(itemRegistry.moldedIron, 3, 0);
		recipe.setRequiredHeat(150).setDuration(300);
		recipe_hm_warslammer = recipe.build();

		//

		/*builder.setProduct(new ItemStack(itemRegistry.weapon_hm_rathalos));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRathalos.RathalosSubType.SHELL, 8));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemRathalos.RathalosSubType.MARROW, 2));
		builder.addIngredient(itemRegistry.firestone, 1, 0);
		builder.setRequiredHeat(300).setDuration(500);
		recipe_hm_rathalos = builder.build();*/

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_tigrex));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.CLAW, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SCALE, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 5));
		recipe.addIngredient(itemRegistry.wyvernCoin, 10, 0);
		recipe.setRequiredHeat(400).setDuration(700);
		recipe_hm_tigrex = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_kirinspark));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.MANE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.PLATINUMMANE, 6));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.LIGHTCRYSTAL, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.PURECRYSTAL, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.THUNDERTAIL, 1));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.ELTALITE, 4));
		recipe.setRequiredHeat(750).setDuration(1200);
		recipe_hm_kirin = recipe.build();
		;

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_devilsdue));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.FANG, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALE, 4));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemDeviljho.DeviljhoSubType.SCALP, 6));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 4));
		recipe.setRequiredHeat(750).setDuration(1800);
		recipe_hm_devilsdue = recipe.build();

		//
		// Longsword class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_ironkatana));
		recipe.addIngredient(itemRegistry.steelbar, 7, 0);
		recipe.addIngredient(itemRegistry.lumberbar, 2, 0);
		recipe.setRequiredHeat(150).setDuration(600);
		recipe_ls_ironkatana = recipe.build();
		
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_ironkatanagrace));
		recipe.addIngredient(itemRegistry.steelbar, 2, 0);
		recipe.addIngredient(itemRegistry.lumberbar, 5, 0);
		recipe.addIngredient(itemRegistry.weapon_ls_ironkatana, 1, 0);
		recipe.setRequiredHeat(150).setDuration(600);
		recipe_ls_ikgrace = recipe.build();
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_ironkatanagospel));
		recipe.addIngredient(itemRegistry.steelbar, 4, 0);
		recipe.addIngredient(itemRegistry.lumberbar, 15	, 0);
		recipe.addIngredient(itemRegistry.weapon_ls_ironkatanagrace, 1, 0);
		recipe.setRequiredHeat(150).setDuration(600);
		recipe_ls_ikgospel = recipe.build();
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_eagercleaver));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.ELECTRO, 4)); // to be replaced with Electro Sac
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); //to be replaced with earth crystal
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 15)); 
		recipe.setRequiredHeat(150).setDuration(900);
		recipe_ls_eagercleaver = recipe.build();
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_devilslicer));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 20)); 
		recipe.addIngredient(itemRegistry.steelbar, 30,0); // to be replaced with thunder bug juice
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.THUNDERTAIL, 2));
		recipe.addIngredient(itemRegistry.weapon_ls_eagercleaver, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_devilslicer = recipe.build();
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_truedevilslicer));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.CARBALITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 20)); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.THUNDER, 4)); //to be replaced by thunder sac
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemKirin.KirinSubType.THUNDERTAIL, 1)); // to be replaced with pale khezu sac
		recipe.addIngredient(itemRegistry.weapon_ls_devilslicer, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_truedevilslicer = recipe.build();
		
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_saber));
		recipe.addIngredient(itemRegistry.moldedIron, 15, 0);
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.FLAME, 20)); 
		recipe.addIngredient(itemRegistry.weapon_ls_ironkatanagospel, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_saber = recipe.build();
		
		
		
		// TO BE ADDED WITH LUNASTRA DROPS
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_liondancesaber));
		recipe.addIngredient(itemRegistry.moldedIron, 15, 0);  
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.FLAME, 20)); 
		recipe.addIngredient(itemRegistry.weapon_ls_ironkatanagospel, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_saberliondance = recipe.build();
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_lionkingsaber));
		recipe.addIngredient(itemRegistry.moldedIron, 15, 0);
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.FLAME, 20)); 
		recipe.addIngredient(itemRegistry.weapon_ls_liondancesaber, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_saberlionking = recipe.build();
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_lionkaisersaber));
		recipe.addIngredient(itemRegistry.moldedIron, 15, 0);
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.FLAME, 20)); 
		recipe.addIngredient(itemRegistry.weapon_ls_lionkingsaber, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_saberlionkaiser = recipe.build();
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_lionsroarsaber));
		recipe.addIngredient(itemRegistry.moldedIron, 15, 0);
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.FLAME, 20)); 
		recipe.addIngredient(itemRegistry.weapon_ls_lionkaisersaber, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_saberlionroar = recipe.build();
		
		
		
		// Phantom Mirage to be added drops
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_miragefinsword));
		recipe.addIngredient(itemRegistry.moldedIron, 30, 0);
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(itemRegistry.weapon_ls_lionkaisersaber, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_miragefinsword = recipe.build();
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_miragefinswordplus));
		recipe.addIngredient(itemRegistry.moldedIron, 30, 0);
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.SCREAMER, 20)); 
		recipe.addIngredient(itemRegistry.weapon_ls_miragefinsword, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_miragefinswordplus = recipe.build();
		
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_phantommirage));
		recipe.addIngredient(itemRegistry.moldedIron, 15, 0);
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); 
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemSac.SacSubType.SCREAMER, 20)); 
		recipe.addIngredient(itemRegistry.weapon_ls_miragefinswordplus, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_miragephantom = recipe.build();
		
		
		
		
		
		
		
		
		
		
		
		

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_darkvipern));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemRemobra.RemobraSubType.SKIN, 6));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemRemobra.RemobraSubType.WING, 2));
		recipe.setRequiredHeat(500).setDuration(750);
		recipe_ls_darkvipern = recipe.build();
		
		
		
		
		
		
		
		
		

		//
		// Hunting Horn Class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hh_ivoryhorn));
		recipe.setRequiredHeat(100).setDuration(250);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 3));
		recipe.addIngredient(itemRegistry.lumberbar, 1, 0);
		recipe.addIngredient(Item.getItemById(352), 6, 0);
		recipe_hh_ivoryhorn = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hh_metalbagpipe));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 3));
		recipe.addIngredient(itemRegistry.moldedIron, 2, 0);
		recipe.setRequiredHeat(200).setDuration(400);
		recipe_hh_metalbagpipe = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hh_tigrex));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.SHELL, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemTigrex.TigrexSubType.FANG, 7));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.DRAGONITE, 10));
		recipe.setRequiredHeat(350).setDuration(600);
		recipe_hh_tigrex = recipe.build();

		//Bow Class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_hunters));
		recipe.addIngredient(itemRegistry.lumberbar, 15, 0);
		recipe.addIngredient(Item.getItemById(265), 12, 0);
		recipe.addIngredient(Item.getItemById(352), 12, 0);
		recipe.setRequiredHeat(350).setDuration(600);
		recipe_b_hunters = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_huntersstout));
		recipe.addIngredient(itemRegistry.lumberbar, 5, 0);
		recipe.addIngredient(Item.getItemById(265), 15, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemIngot.IngotsSubType.MACHALITE, 15));
		recipe.setRequiredHeat(350).setDuration(600);
		recipe_b_huntersstout = recipe.build();

	}

}
