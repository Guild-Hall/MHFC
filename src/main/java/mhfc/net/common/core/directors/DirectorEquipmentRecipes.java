package mhfc.net.common.core.directors;

import mhfc.net.common.core.builders.BuilderEquipmentRecipe;
import mhfc.net.common.core.data.EquipmentRecipeRegistryData;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.crafting.equipment.EquipmentRecipe;
import mhfc.net.common.item.materials.ItemMaterial;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DirectorEquipmentRecipes {

	// Armors
	public final EquipmentRecipe recipe_tigrex_helm;
	public final EquipmentRecipe recipe_tigrex_chest;
	public final EquipmentRecipe recipe_tigrex_legs;
	public final EquipmentRecipe recipe_tigrex_boots;

	public final EquipmentRecipe recipe_barroth_helm;
	public final EquipmentRecipe recipe_barroth_chest;
	public final EquipmentRecipe recipe_barroth_legs;
	public final EquipmentRecipe recipe_barroth_boots;

	public final EquipmentRecipe recipe_yukumo_helm;
	public final EquipmentRecipe recipe_yukumo_chest;
	public final EquipmentRecipe recipe_yukumo_legs;
	public final EquipmentRecipe recipe_yukumo_boots;

	public final EquipmentRecipe recipe_vangis_helm;
	public final EquipmentRecipe recipe_vangis_chest;
	public final EquipmentRecipe recipe_vangis_legs;
	public final EquipmentRecipe recipe_vangis_boots;

	//	public final EquipmentRecipe recipe_rathalos_helm;
	//	public final EquipmentRecipe recipe_rathalos_chest;
	//	public final EquipmentRecipe recipe_rathalos_legs;
	//	public final EquipmentRecipe recipe_rathalos_boots;

	//	public final EquipmentRecipe recipe_kirin_helm;

	// Weapon
	
	public final EquipmentRecipe recipe_gs_bone;
	//	public final EquipmentRecipe recipe_gs_deadlyserpentblade;
	public final EquipmentRecipe recipe_gs_redwing;
	//	public final EquipmentRecipe recipe_gs_thundersword;
	public final EquipmentRecipe recipe_gs_agito;
	public final EquipmentRecipe recipe_gs_berserker;
	

	public final EquipmentRecipe recipe_hm_war;
	public final EquipmentRecipe recipe_hm_warplus;
	public final EquipmentRecipe recipe_hm_warslammer;
	public final EquipmentRecipe recipe_hm_tigrex;
	//	public final EquipmentRecipe recipe_hm_kirin;
//	public final EquipmentRecipe recipe_hm_rathalos;
	public final EquipmentRecipe recipe_hm_devilsdue;

	public final EquipmentRecipe recipe_ls_ironkatana;
	public final EquipmentRecipe recipe_ls_ikgrace;
	public final EquipmentRecipe recipe_ls_ikgospel;
	public final EquipmentRecipe recipe_ls_eagercleaver;
	public final EquipmentRecipe recipe_ls_devilslicer;
	public final EquipmentRecipe recipe_ls_truedevilslicer;
	//	public final EquipmentRecipe recipe_ls_darkvipern;
	//	public final EquipmentRecipe recipe_ls_saber;
	//	public final EquipmentRecipe recipe_ls_saberliondance;
	//	public final EquipmentRecipe recipe_ls_saberlionking;
	//	public final EquipmentRecipe recipe_ls_saberlionkaiser;
	//	public final EquipmentRecipe recipe_ls_saberlionroar;
	//	public final EquipmentRecipe recipe_ls_miragefinsword;
	//	public final EquipmentRecipe recipe_ls_miragefinswordplus;
	//	public final EquipmentRecipe recipe_ls_miragephantom;

	public final EquipmentRecipe recipe_hh_ivoryhorn;
	public final EquipmentRecipe recipe_hh_metalbagpipe;
	public final EquipmentRecipe recipe_hh_tigrex;

	public final EquipmentRecipe recipe_b_huntersI;
	public final EquipmentRecipe recipe_b_huntersII;
	public final EquipmentRecipe recipe_b_huntersIII;
	public final EquipmentRecipe recipe_b_huntersstoutI;
	public final EquipmentRecipe recipe_b_huntersstoutII;
	public final EquipmentRecipe recipe_b_huntersproud;

	public void construct(EquipmentRecipeRegistryData director) {

		director.register(recipe_yukumo_helm);
		director.register(recipe_barroth_helm);
		//director.register(recipe_rathalos_helm);
		director.register(recipe_tigrex_helm);
		//director.register(recipe_kirin_helm);
		director.register(recipe_vangis_helm);

		director.register(recipe_yukumo_chest);
		director.register(recipe_barroth_chest);
		//director.register(recipe_rathalos_chest);
		director.register(recipe_tigrex_chest);
		director.register(recipe_vangis_chest);

		director.register(recipe_yukumo_legs);
		director.register(recipe_barroth_legs);
		//director.register(recipe_rathalos_legs);
		director.register(recipe_tigrex_legs);
		director.register(recipe_vangis_legs);

		director.register(recipe_yukumo_boots);
		director.register(recipe_barroth_boots);
		//director.register(recipe_rathalos_boots);
		director.register(recipe_tigrex_boots);
		director.register(recipe_vangis_boots);

		// Weapon
		
		director.register(recipe_gs_bone);
		//director.register(recipe_gs_deadlyserpentblade);
		director.register(recipe_gs_redwing);
		//director.register(recipe_gs_thundersword);
		director.register(recipe_gs_berserker);
		director.register(recipe_gs_agito);
		
		
		
		
		director.register(recipe_hm_war);
		director.register(recipe_hm_warplus);
		director.register(recipe_hm_warslammer);
		director.register(recipe_hm_tigrex);
		//director.register(recipe_hm_kirin);
	//	dataObject.register(recipe_hm_rathalos);
		director.register(recipe_hm_devilsdue);
		
		
		director.register(recipe_ls_ironkatana);
		director.register(recipe_ls_ikgrace);
		director.register(recipe_ls_ikgospel);
		director.register(recipe_ls_eagercleaver);
		director.register(recipe_ls_devilslicer);
		director.register(recipe_ls_truedevilslicer);
		//director.register(recipe_ls_darkvipern);
		//director.register(recipe_ls_saber);
		//director.register(recipe_ls_saberliondance);
		//director.register(recipe_ls_saberlionking);
		//director.register(recipe_ls_saberlionkaiser);
		//director.register(recipe_ls_saberlionroar);
		//director.register(recipe_ls_miragefinsword);
		//director.register(recipe_ls_miragefinswordplus);
		//director.register(recipe_ls_miragephantom);
		//director.register(recipe_ls_darkvipern);
	
		
		director.register(recipe_hh_ivoryhorn);
		director.register(recipe_hh_metalbagpipe);
		director.register(recipe_hh_tigrex);

		director.register(recipe_b_huntersI);
		director.register(recipe_b_huntersII);
		director.register(recipe_b_huntersIII);
		director.register(recipe_b_huntersstoutI);
		director.register(recipe_b_huntersstoutII);
		director.register(recipe_b_huntersproud);

	}

	public DirectorEquipmentRecipes() {

		BuilderEquipmentRecipe recipe = new BuilderEquipmentRecipe();
		MHFCItemRegistry itemRegistry = MHFCItemRegistry.getRegistry();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_helm));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSKULLSHELL, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSCALE, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSHELL, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXCLAW, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 5));
		recipe.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_chest));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXFANG, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXCLAW, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSHELL, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSCALE, 25));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXTAIL, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSKULLSHELL, 2));
		recipe.setRequiredHeat(200).setDuration(600);
		recipe_tigrex_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_legs));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXCLAW, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSCALE, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXTAIL, 1));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 10));
		recipe.setRequiredHeat(200).setDuration(400);
		recipe_tigrex_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_tigrex_boots));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXFANG, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSCALE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSHELL, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 5));
		recipe.setRequiredHeat(200).setDuration(300);
		recipe_tigrex_boots = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_barroth_helm));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSHELL, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHRIDGE, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSCALP, 8));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHTAIL, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSTONE, 3));
		recipe.setRequiredHeat(200).setDuration(300);
		recipe_barroth_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_barroth_chest));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSHELL, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSCALP, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHTAIL, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHCLAW, 25));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSTONE, 10));
		recipe.setRequiredHeat(200).setDuration(300);
		recipe_barroth_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_barroth_legs));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSHELL, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSCALP, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHTAIL, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHCLAW, 25));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHMUD, 10));
		recipe.setRequiredHeat(200).setDuration(300);
		recipe_barroth_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_barroth_boots));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSHELL, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHSCALP, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHTAIL, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHCLAW, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BARROTHMUD, 10));
		recipe.setRequiredHeat(200).setDuration(300);
		recipe_barroth_boots = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_helm));
		recipe.addIngredient(itemRegistry.lumberbar, 15, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.ANUMIUM, 5));
		recipe.addIngredient(Item.getItemById(265), 15, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_chest));
		recipe.addIngredient(itemRegistry.lumberbar, 15, 0);
		recipe.addIngredient(Item.getItemById(265), 50, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DELEXBFIN, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DELEXFANG, 4));
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_legs));
		recipe.addIngredient(itemRegistry.lumberbar, 20, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_yukumo_boots));
		recipe.addIngredient(itemRegistry.lumberbar, 10, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_yukumo_boots = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_helm));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOFANG, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOHIDE, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALP, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 2));// Will be replaced by Conquerors Seal
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_helm = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_chest));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOFANG, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOHIDE, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.RATHALOSWEBBING, 8));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.CARBALITE, 4));// Will be Replaced by Dragonbone Relic
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_chest = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_legs));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALE, 12));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOTAIL, 1)); // Will be replaced by Gem
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALP, 4)); // Will be replaced by Rath Medul
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.CARBALITE, 4)); //Will be replaced by Dragonbone Relic
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_legs = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.armor_vangis_boots));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALE, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOTALON, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOTAIL, 1)); //gem
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 2)); //dragonbonerelic
		recipe.setRequiredHeat(200).setDuration(1500);
		recipe_vangis_boots = recipe.build();



		// GreatSword

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_bone));
		recipe.addIngredient(Item.getItemById(265), 50, 0);
		recipe.addIngredient(Item.getItemById(352), 25, 0);
		recipe.addIngredient(itemRegistry.lumberbar, 4, 0);
		recipe.setRequiredHeat(100).setDuration(300);
		recipe_gs_bone = recipe.build();
		
		
		

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_rathalosfire));
		recipe.addIngredient(SubTypedItem.fromSubItem(MaterialSubType.RATHALOSWING, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(MaterialSubType.FLAME, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(MaterialSubType.RATHALOSMARROW, 15));
		recipe.addIngredient(itemRegistry.firestone, 3, 0);
		recipe.setRequiredHeat(300).setDuration(900);
		recipe_gs_redwing = recipe.build();



		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_tigrex));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSCALE, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXFANG, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSHELL, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSKULLSHELL, 1));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 2));
		recipe.setRequiredHeat(500).setDuration(1000);
		recipe_gs_agito = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_gs_berserkers));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOFANG, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALE, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOTALON, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALP, 3));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 5));
		recipe.setRequiredHeat(600).setDuration(1500);
		recipe_gs_berserker = recipe.build();

		//

		// Hammer Class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_warhammer));
		recipe.addIngredient(Items.IRON_INGOT, 50, 0);
		recipe.addIngredient(itemRegistry.steelbar, 4, 0);
		recipe.setRequiredHeat(100).setDuration(200);

		recipe_hm_war = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_warhammerplus));
		recipe.addIngredient(itemRegistry.weapon_hm_warhammer, 1, 0);
		recipe.addIngredient(itemRegistry.steelbar, 15, 0);
		recipe.addIngredient(itemRegistry.moldedIron, 10, 0);
		recipe.addIngredient(Items.IRON_INGOT, 5, 0);
		recipe.setRequiredHeat(100).setDuration(250);
		recipe_hm_warplus = recipe.build();

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_warslammer));
		recipe.addIngredient(itemRegistry.weapon_hm_warhammerplus, 1, 0);
		recipe.addIngredient(itemRegistry.steelbar, 15, 0);
		recipe.addIngredient(itemRegistry.moldedIron, 10, 0);
		recipe.setRequiredHeat(150).setDuration(300);
		recipe_hm_warslammer = recipe.build();

		//

		/*builder.setProduct(new ItemStack(itemRegistry.weapon_hm_rathalos));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.SHELL, 8));
		builder.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MARROW, 2));
		builder.addIngredient(itemRegistry.firestone, 1, 0);
		builder.setRequiredHeat(300).setDuration(500);
		recipe_hm_rathalos = builder.build();*/

		//

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_tigrex));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXCLAW, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSCALE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSHELL, 15));
		recipe.setRequiredHeat(400).setDuration(700);
		recipe_hm_tigrex = recipe.build();

		//


		recipe.setProduct(new ItemStack(itemRegistry.weapon_hm_devilsdue));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOFANG, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALE, 15));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DEVILJHOSCALP, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 4));
		recipe.setRequiredHeat(750).setDuration(1800);
		recipe_hm_devilsdue = recipe.build();

		//
		// Longsword class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_ironkatana));
		recipe.addIngredient(itemRegistry.steelbar, 7, 0);
		recipe.addIngredient(itemRegistry.lumberbar, 2, 0);
		recipe.setRequiredHeat(150).setDuration(600);
		recipe_ls_ironkatana = recipe.build();
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
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.ELECTRO, 4)); // to be replaced with Electro Sac
		recipe.addIngredient(itemRegistry.steelbar, 30	, 0); //to be replaced with earth crystal
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 15));
		recipe.setRequiredHeat(150).setDuration(900);
		recipe_ls_eagercleaver = recipe.build();
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_devilslicer));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 20));
		recipe.addIngredient(itemRegistry.steelbar, 30,0); // to be replaced with thunder bug juice
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.KIRINTHUNDERTAIL, 2));
		recipe.addIngredient(itemRegistry.weapon_ls_eagercleaver, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_devilslicer = recipe.build();
		
		recipe.setProduct(new ItemStack(itemRegistry.weapon_ls_truedevilslicer));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.CARBALITE, 10));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 20));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.THUNDER, 4)); //to be replaced by thunder sac
		recipe.addIngredient(itemRegistry.weapon_ls_devilslicer, 1, 0);
		recipe.setRequiredHeat(450).setDuration(1200);
		recipe_ls_truedevilslicer = recipe.build();
		// Hunting Horn Class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hh_ivoryhorn));
		recipe.setRequiredHeat(50).setDuration(200);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BRUTEBONE, 2));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.BLUEMUSHROOM, 1));
		recipe.addIngredient(itemRegistry.lumberbar, 5, 0);
		recipe_hh_ivoryhorn = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hh_metalbagpipe));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 3));
		recipe.addIngredient(itemRegistry.moldedIron, 2, 0);
		recipe.setRequiredHeat(200).setDuration(400);
		recipe_hh_metalbagpipe = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_hh_tigrex));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXSHELL, 5));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.TIGREXFANG, 7));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.DRAGONITE, 10));
		recipe.setRequiredHeat(350).setDuration(600);
		recipe_hh_tigrex = recipe.build();

		//Bow Class

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_huntersI));
		recipe.addIngredient(Item.getItemById(261), 1, 0);
		recipe.addIngredient(Item.getItemById(265), 50, 0);
		recipe.addIngredient(Item.getItemById(352), 35, 0);
		recipe.setRequiredHeat(50).setDuration(200);
		recipe_b_huntersI = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_huntersII));
		recipe.addIngredient(itemRegistry.weapon_b_huntersI, 1, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 4));
		recipe.addIngredient(Item.getItemById(352), 50, 0);
		recipe.setRequiredHeat(50).setDuration(200);
		recipe_b_huntersII = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_huntersIII));
		recipe.addIngredient(itemRegistry.weapon_b_huntersII, 1, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 10));
		recipe.setRequiredHeat(50).setDuration(200);
		recipe_b_huntersIII = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_huntersstoutI));
		recipe.addIngredient(itemRegistry.weapon_b_huntersIII, 1, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.CARBALITE, 3));
		recipe.addIngredient(Item.getItemById(265), 30, 0);
		recipe.setRequiredHeat(50).setDuration(200);
		recipe_b_huntersstoutI = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_huntersstoutII));
		recipe.addIngredient(itemRegistry.weapon_b_huntersstoutI, 1, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.MACHALITE, 25));
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.CARBALITE, 5));
		recipe.setRequiredHeat(50).setDuration(200);
		recipe_b_huntersstoutII = recipe.build();

		recipe.setProduct(new ItemStack(itemRegistry.weapon_b_huntersproud));
		recipe.addIngredient(itemRegistry.weapon_b_huntersstoutII, 1, 0);
		recipe.addIngredient(Item.getItemById(265), 50, 0);
		recipe.addIngredient(SubTypedItem.fromSubItem(ItemMaterial.MaterialSubType.ELTALITE, 5));
		recipe.setRequiredHeat(50).setDuration(200);
		recipe_b_huntersproud = recipe.build();

	}

}
