package mhfc.net.common.core.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.WeaponData;
import mhfc.net.common.item.armor.donators.DragoonArmor;
import mhfc.net.common.item.armor.donators.ST_BionicArmor;
import mhfc.net.common.item.armor.generic.BarrothArmor;
import mhfc.net.common.item.armor.generic.GreatJaggiArmor;
import mhfc.net.common.item.armor.generic.KirinArmor;
import mhfc.net.common.item.armor.generic.KirinSArmor;
import mhfc.net.common.item.armor.generic.KishinArmor;
import mhfc.net.common.item.armor.generic.NibelsnarfArmor;
import mhfc.net.common.item.armor.generic.RathalosArmor;
import mhfc.net.common.item.armor.generic.TigrexArmor;
import mhfc.net.common.item.armor.generic.VangisArmor;
import mhfc.net.common.item.armor.generic.VelocipreyArmor;
import mhfc.net.common.item.armor.generic.YukumoArmor;
import mhfc.net.common.item.food.ItemKirinBuff;
import mhfc.net.common.item.food.ItemMeats;
import mhfc.net.common.item.food.ItemNutrients;
import mhfc.net.common.item.materials.ItemArmorSphere;
import mhfc.net.common.item.materials.ItemBase;
import mhfc.net.common.item.materials.ItemBombMaterial;
import mhfc.net.common.item.materials.ItemBullet;
import mhfc.net.common.item.materials.ItemDeviljho;
import mhfc.net.common.item.materials.ItemFirestone;
import mhfc.net.common.item.materials.ItemFlashBomb;
import mhfc.net.common.item.materials.ItemGaguaEgg;
import mhfc.net.common.item.materials.ItemIngot;
import mhfc.net.common.item.materials.ItemKirin;
import mhfc.net.common.item.materials.ItemLumberBar;
import mhfc.net.common.item.materials.ItemMoldedIron;
import mhfc.net.common.item.materials.ItemRathalos;
import mhfc.net.common.item.materials.ItemRemobra;
import mhfc.net.common.item.materials.ItemSac;
import mhfc.net.common.item.materials.ItemSpawner;
import mhfc.net.common.item.materials.ItemSteelBar;
import mhfc.net.common.item.materials.ItemTigrex;
import mhfc.net.common.item.materials.ItemTrapTool;
import mhfc.net.common.item.materials.ItemWoodRig;
import mhfc.net.common.item.materials.ItemWyvernCoin;
import mhfc.net.common.item.materials.ItemWyverniaArrow;
import mhfc.net.common.item.materials.ItemWyverniaClay;
import mhfc.net.common.item.tools.ItemPaintball;
import mhfc.net.common.util.services.IServiceKey;
import mhfc.net.common.weapon.melee.greatsword.ItemGreatsword;
import mhfc.net.common.weapon.melee.hammer.ItemHammer;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import mhfc.net.common.weapon.melee.longsword.ItemLongsword;
import mhfc.net.common.weapon.range.bow.ItemBow;
import mhfc.net.common.weapon.range.bowgun.heavy.ItemHeavyBowgun;
import mhfc.net.common.weapon.range.bowgun.light.ItemLightBowgun;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCItemRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCItemRegistry> serviceAccess = RegistryWrapper
			.registerService("item registry", MHFCItemRegistry::new, MHFCMain.preInitPhase);

	/**
	 * Please Arrange The Weapons by its RARITY refer to the weapon package ~@Heltrato
	 */
	// Weapons

	public final ItemGreatsword weapon_gs_bone;
	public final ItemGreatsword weapon_gs_tigrex;
	public final ItemGreatsword weapon_gs_kirinthunders;
	public final ItemGreatsword weapon_gs_berserkers;
	public final ItemGreatsword weapon_gs_rathalosfire;
	public final ItemGreatsword weapon_gs_deadlyserpentblade;

	public final ItemHammer weapon_hm_tigrex;
	public final ItemHammer weapon_hm_kirinspark;
	public final ItemHammer weapon_hm_warhammer;
	public final ItemHammer weapon_hm_warhammerplus;
	public final ItemHammer weapon_hm_warslammer;
	public final ItemHammer weapon_hm_devilsdue;
	//public final ItemHammer weapon_hm_rathalos;

	public final ItemLongsword weapon_ls_ironkatana;
	public final ItemLongsword weapon_ls_ironkatanagrace;
	public final ItemLongsword weapon_ls_ironkatanagospel;
	public final ItemLongsword weapon_ls_eagercleaver;
	public final ItemLongsword weapon_ls_devilslicer;
	public final ItemLongsword weapon_ls_truedevilslicer;
	public final ItemLongsword weapon_ls_darkvipern;
	public final ItemLongsword weapon_ls_saber;
	public final ItemLongsword weapon_ls_liondancesaber;
	public final ItemLongsword weapon_ls_lionkingsaber;
	public final ItemLongsword weapon_ls_lionkaisersaber;
	public final ItemLongsword weapon_ls_lionsroarsaber;
	public final ItemLongsword weapon_ls_miragefinsword;
	public final ItemLongsword weapon_ls_miragefinswordplus;
	public final ItemLongsword weapon_ls_phantommirage;

	public final ItemHuntingHorn weapon_hh_metalbagpipe;
	public final ItemHuntingHorn weapon_hh_ivoryhorn;
	public final ItemHuntingHorn weapon_hh_tigrex;
	public final ItemHuntingHorn weapon_hh_greatbagpipe;
	public final ItemHuntingHorn weapon_hh_heavybagpipe;
	public final ItemHuntingHorn weapon_hh_heavybagpipeplus;
	public final ItemHuntingHorn weapon_hh_elitebagpipe;
	public final ItemHuntingHorn weapon_hh_wardrums;
	public final ItemHuntingHorn weapon_hh_wardrumsplus;
	public final ItemHuntingHorn weapon_hh_mogwarddrums;
	public final ItemHuntingHorn weapon_hh_blackcasket;
	public final ItemHuntingHorn weapon_hh_darkthorntrumpet;

	public final ItemLightBowgun weapon_bgl_barrel;

	public final ItemHeavyBowgun weapon_bgh_rath;

	public final ItemBow weapon_b_hunters;
	public final ItemBow weapon_b_huntersstout;
	public final ItemBow weapon_b_huntersproud;

	// Armors
	public final Item armor_tigrex_helm;
	public final Item armor_tigrex_chest;
	public final Item armor_tigrex_legs;
	public final Item armor_tigrex_boots;

	public final Item armor_kirin_helm;
	public final Item armor_kirin_chest;
	public final Item armor_kirin_legs;
	public final Item armor_kirin_boots;

	public final Item armor_kirinS_helm;
	public final Item armor_kirinS_chest;
	public final Item armor_kirinS_legs;
	public final Item armor_kirinS_boots;

	public final Item armor_yukumo_helm;
	public final Item armor_yukumo_chest;
	public final Item armor_yukumo_legs;
	public final Item armor_yukumo_boots;

	public final Item armor_rathalos_helm;
	public final Item armor_rathalos_chest;
	public final Item armor_rathalos_legs;
	public final Item armor_rathalos_boots;

	public final Item armor_dragoon_helm;
	public final Item armor_dragoon_chest;
	public final Item armor_dragoon_legs;
	public final Item armor_dragoon_boots;

	public final Item armor_velociprey_helm;
	public final Item armor_velociprey_chest;
	public final Item armor_velociprey_legs;
	public final Item armor_velociprey_boots;

	public final Item armor_nibelsnarf_helm;
	public final Item armor_nibelsnarf_chest;
	public final Item armor_nibelsnarf_legs;
	public final Item armor_nibelsnarf_boots;


	public final Item armor_tigrexB_helm;
	public final Item armor_tigrexB_chest;
	public final Item armor_tigrexB_legs;
	public final Item armor_tigrexB_boots;

	public final Item armor_bionic_helm;
	public final Item armor_bionic_chest;
	public final Item armor_bionic_legs;
	public final Item armor_bionic_boots;

	public final Item armor_vangis_helm;
	public final Item armor_vangis_chest;
	public final Item armor_vangis_legs;
	public final Item armor_vangis_boots;

	public final Item armor_jaggi_helm;
	public final Item armor_jaggi_chest;
	public final Item armor_jaggi_legs;
	public final Item armor_jaggi_boots;

	public final Item armor_barroth_helm;
	public final Item armor_barroth_chest;
	public final Item armor_barroth_legs;
	public final Item armor_barroth_boots;

	// Materials

	public final ItemTigrex tigrexdrops;
	public final ItemKirin kirindrops;
	public final ItemRemobra remobradrops;
	// public  final Item mhfcitemlightcrystal;
	// public  final Item mhfcitempurecrystal;
	public final ItemRathalos rathalosdrops;
	public final ItemDeviljho deviljhodrops;
	public final ItemBullet bowgunBullet;

	public final Item woodrig;
	public final Item lumberbar;
	public final Item steelbar;
	public final Item trapTool;
	public final ItemFlashBomb flashBomb;
	public final Item bombMaterial;
	public final Item gaguaEgg;
	public final ItemWyvernCoin wyvernCoin;
	public final Item paintball;
	public final Item arrow;

	public final Item ingot;
	public final Item base;
	public final Item armorsphere;
	public final Item sac;

	public final Item moldedIron;
	public final ItemFirestone firestone;
	public final Item wyverniaClay;

	// Foods
	public final ItemKirinBuff kirinbuff;
	public final ItemMeats meat;
	public final ItemNutrients nutrients;

	// Spawners.(They must be Last)
	public final ItemSpawner MHFCItemFrontierSpawner;

	private final List<Item> everyItems = new ArrayList<>();
	public final Collection<Item> allItems = Collections.unmodifiableList(everyItems);

	private MHFCItemRegistry() {
		/*
		 * @author Heltrato: "Please sort the weapon by there Rarity.. Thanks"
		 *
		 */

		// Armor
		armor_yukumo_helm = registerItem("arm_yukumo_helmet", new YukumoArmor(EntityEquipmentSlot.HEAD));
		armor_velociprey_helm = registerItem("arm_veloci_helmet", new VelocipreyArmor(EntityEquipmentSlot.HEAD));
		armor_jaggi_helm = registerItem("arm_great_jaggi_helmet", new GreatJaggiArmor(EntityEquipmentSlot.HEAD));
		armor_rathalos_helm = registerItem("arm_rathalos_helmet", new RathalosArmor(EntityEquipmentSlot.HEAD));
		armor_nibelsnarf_helm = registerItem("arm_nibelsnarf_helmet", new NibelsnarfArmor(EntityEquipmentSlot.HEAD));
		armor_barroth_helm = registerItem("arm_barroth_helmet", new BarrothArmor(EntityEquipmentSlot.HEAD));
		armor_tigrex_helm = registerItem("arm_tigrex_helmet", new TigrexArmor(EntityEquipmentSlot.HEAD));
		armor_kirin_helm = registerItem("arm_kirin_helmet", new KirinArmor(EntityEquipmentSlot.HEAD));
		armor_vangis_helm = registerItem("arm_deviljho_helmet", new VangisArmor(EntityEquipmentSlot.HEAD));
		armor_tigrexB_helm = registerItem("arm_tigrex_plus_helmet", new KishinArmor(EntityEquipmentSlot.HEAD));
		armor_dragoon_helm = registerItem("arm_dragoon_helmet", new DragoonArmor(EntityEquipmentSlot.HEAD));
		armor_kirinS_helm = registerItem("arm_kirin_s_helmet", new KirinSArmor(EntityEquipmentSlot.HEAD));
		armor_bionic_helm = registerItem("arm_bionic_helmet", new ST_BionicArmor(EntityEquipmentSlot.HEAD));

		armor_yukumo_chest = registerItem("arm_yukumo_chestplate", new YukumoArmor(EntityEquipmentSlot.CHEST));
		armor_velociprey_chest = registerItem("arm_veloci_chestplate", new VelocipreyArmor(EntityEquipmentSlot.CHEST));
		armor_jaggi_chest = registerItem("arm_great_jaggi_chestplate", new GreatJaggiArmor(EntityEquipmentSlot.CHEST));
		armor_rathalos_chest = registerItem("arm_rathalos_chestplate", new RathalosArmor(EntityEquipmentSlot.CHEST));
		armor_nibelsnarf_chest = registerItem("arm_nibelsnarf_chestplate",new NibelsnarfArmor(EntityEquipmentSlot.CHEST));
		armor_barroth_chest = registerItem("arm_barroth_chestplate", new BarrothArmor(EntityEquipmentSlot.CHEST));
		armor_tigrex_chest = registerItem("arm_tigrex_chestplate", new TigrexArmor(EntityEquipmentSlot.CHEST));
		armor_kirin_chest = registerItem("arm_kirin_chestplate", new KirinArmor(EntityEquipmentSlot.CHEST));
		armor_vangis_chest = registerItem("arm_deviljho_chestplate", new VangisArmor(EntityEquipmentSlot.CHEST));
		armor_tigrexB_chest = registerItem("arm_tigrex_plus_chestplate", new KishinArmor(EntityEquipmentSlot.CHEST));
		armor_dragoon_chest = registerItem("arm_dragoon_chestplate", new DragoonArmor(EntityEquipmentSlot.CHEST));
		armor_kirinS_chest = registerItem("arm_kirin_s_chestplate", new KirinSArmor(EntityEquipmentSlot.CHEST));
		armor_bionic_chest = registerItem("arm_bionic_chestplate", new ST_BionicArmor(EntityEquipmentSlot.CHEST));

		armor_yukumo_legs = registerItem("arm_yukumo_leggings", new YukumoArmor(EntityEquipmentSlot.LEGS));
		armor_velociprey_legs = registerItem("arm_veloci_leggings", new VelocipreyArmor(EntityEquipmentSlot.LEGS));
		armor_jaggi_legs = registerItem("arm_great_jaggi_leggings", new GreatJaggiArmor(EntityEquipmentSlot.LEGS));
		armor_rathalos_legs = registerItem("arm_rathalos_leggings", new RathalosArmor(EntityEquipmentSlot.LEGS));
		armor_nibelsnarf_legs = registerItem("arm_nibelsnarf_leggings", new NibelsnarfArmor(EntityEquipmentSlot.LEGS));
		armor_barroth_legs = registerItem("arm_barroth_leggings", new BarrothArmor(EntityEquipmentSlot.LEGS));
		armor_tigrex_legs = registerItem("arm_tigrex_leggings", new TigrexArmor(EntityEquipmentSlot.LEGS));
		armor_kirin_legs = registerItem("arm_kirin_leggings", new KirinArmor(EntityEquipmentSlot.LEGS));
		armor_vangis_legs = registerItem("arm_deviljho_leggings", new VangisArmor(EntityEquipmentSlot.LEGS));
		armor_tigrexB_legs = registerItem("arm_tigrex_plus_leggings", new KishinArmor(EntityEquipmentSlot.LEGS));
		armor_dragoon_legs = registerItem("arm_dragoon_leggings", new DragoonArmor(EntityEquipmentSlot.LEGS));
		armor_kirinS_legs = registerItem("arm_kirin_s_leggings", new KirinSArmor(EntityEquipmentSlot.LEGS));
		armor_bionic_legs = registerItem("arm_bionic_leggings", new ST_BionicArmor(EntityEquipmentSlot.LEGS));

		armor_yukumo_boots = registerItem("arm_yukumo_boots", new YukumoArmor(EntityEquipmentSlot.FEET));
		armor_velociprey_boots = registerItem("arm_veloci_boots", new VelocipreyArmor(EntityEquipmentSlot.FEET));
		armor_jaggi_boots = registerItem("arm_great_jaggi_boots", new GreatJaggiArmor(EntityEquipmentSlot.FEET));
		armor_rathalos_boots = registerItem("arm_rathalos_boots", new RathalosArmor(EntityEquipmentSlot.FEET));
		armor_nibelsnarf_boots = registerItem("arm_nibelsnarf_boots", new NibelsnarfArmor(EntityEquipmentSlot.FEET));
		armor_barroth_boots = registerItem("arm_barroth_boots", new BarrothArmor(EntityEquipmentSlot.FEET));
		armor_tigrex_boots = registerItem("arm_tigrex_boots", new TigrexArmor(EntityEquipmentSlot.FEET));
		armor_kirin_boots = registerItem("arm_kirin_boots", new KirinArmor(EntityEquipmentSlot.FEET));
		armor_vangis_boots = registerItem("arm_deviljho_boots", new VangisArmor(EntityEquipmentSlot.FEET));
		armor_tigrexB_boots = registerItem("arm_tigrex_plus_boots", new KishinArmor(EntityEquipmentSlot.FEET));
		armor_dragoon_boots = registerItem("arm_dragoon_boots", new DragoonArmor(EntityEquipmentSlot.FEET));
		armor_kirinS_boots = registerItem("arm_kirin_s_boots", new KirinSArmor(EntityEquipmentSlot.FEET));
		armor_bionic_boots = registerItem("arm_bionic_boots", new ST_BionicArmor(EntityEquipmentSlot.FEET));

		// Weapons
		WeaponData jsonWeaponData = readLocalWeaponData();
		// FIXME: value tuning
		weapon_gs_bone = registerItem("gs_bone", jsonWeaponData);
		weapon_gs_deadlyserpentblade = registerItem("gs_deadly_serpent", jsonWeaponData);
		weapon_gs_tigrex = registerItem("gs_tigrex", jsonWeaponData);
		weapon_gs_rathalosfire = registerItem("gs_rathalos", jsonWeaponData);
		weapon_gs_kirinthunders = registerItem("gs_kirin", jsonWeaponData);
		weapon_gs_berserkers = registerItem("gs_berserker", jsonWeaponData);

		weapon_ls_ironkatana = registerItem("ls_iron", jsonWeaponData);
		weapon_ls_ironkatanagrace = registerItem("ls_iron_grace", jsonWeaponData);
		weapon_ls_ironkatanagospel = registerItem("ls_iron_gospel", jsonWeaponData);
		weapon_ls_darkvipern = registerItem("ls_darkviper", jsonWeaponData);
		weapon_ls_eagercleaver = registerItem("ls_eagercleaver", jsonWeaponData);
		weapon_ls_devilslicer = registerItem("ls_devilslicer", jsonWeaponData);
		weapon_ls_saber = registerItem("ls_saber", jsonWeaponData);
		weapon_ls_miragefinsword = registerItem("ls_mirage", jsonWeaponData);
		weapon_ls_miragefinswordplus = registerItem("ls_mirage_plus", jsonWeaponData);
		weapon_ls_liondancesaber = registerItem("ls_lion_dance", jsonWeaponData);
		weapon_ls_truedevilslicer = registerItem("ls_devilslider_true", jsonWeaponData);
		weapon_ls_phantommirage = registerItem("ls_mirage_phantom", jsonWeaponData);
		weapon_ls_lionkingsaber = registerItem("ls_lion_king", jsonWeaponData);
		weapon_ls_lionkaisersaber = registerItem("ls_lion_kaiser", jsonWeaponData);
		weapon_ls_lionsroarsaber = registerItem("ls_lion_roar", jsonWeaponData);

		weapon_hm_warhammer = registerItem("hm_war", jsonWeaponData);
		weapon_hm_warhammerplus = registerItem("hm_war_plus", jsonWeaponData);
		weapon_hm_warslammer = registerItem("hm_war_slammer", jsonWeaponData);
		weapon_hm_tigrex = registerItem("hm_tigrex", jsonWeaponData);
		/*weapon_hm_rathalos = registerItem("hm_rathalos", jsonWeaponData);*/
		weapon_hm_devilsdue = registerItem("hm_devil", jsonWeaponData);
		weapon_hm_kirinspark = registerItem("hm_kirin_spark", jsonWeaponData);

		weapon_hh_ivoryhorn = registerItem("hh_ivory", jsonWeaponData);
		weapon_hh_metalbagpipe = registerItem("hh_bagpipe", jsonWeaponData);
		weapon_hh_greatbagpipe = registerItem("hh_bagpipe_great", jsonWeaponData);
		weapon_hh_wardrums = registerItem("hh_wardrums", jsonWeaponData);
		weapon_hh_wardrumsplus = registerItem("hh_wardrums_plus", jsonWeaponData);
		weapon_hh_heavybagpipe = registerItem("hh_bagpipe_heavy", jsonWeaponData);
		weapon_hh_heavybagpipeplus = registerItem("hh_bagpipe_heavy_plus", jsonWeaponData);
		weapon_hh_tigrex = registerItem("hh_tigrex", jsonWeaponData);
		weapon_hh_mogwarddrums = registerItem("hh_wardrums_bongo", jsonWeaponData);
		weapon_hh_elitebagpipe = registerItem("hh_bigpipe_elite", jsonWeaponData);
		weapon_hh_darkthorntrumpet = registerItem("hh_darkthorn", jsonWeaponData);
		weapon_hh_blackcasket = registerItem("hh_black_casket", jsonWeaponData);

		// Range weapons

		weapon_b_hunters = registerItem("b_hunter", jsonWeaponData);
		weapon_b_huntersstout = registerItem("b_hunter_stout", jsonWeaponData);
		weapon_b_huntersproud = registerItem("b_hunter_proud", jsonWeaponData);

		weapon_bgl_barrel = registerItem("bgl_barrel", jsonWeaponData);

		weapon_bgh_rath = registerItem("bgh_spartacus", jsonWeaponData);

		arrow = registerItem("arrow", new ItemWyverniaArrow());
		// Items... drops

		moldedIron = registerItem("iron_molded", new ItemMoldedIron());
		woodrig = registerItem("woodrig", new ItemWoodRig());
		lumberbar = registerItem("lumberbar", new ItemLumberBar());
		steelbar = registerItem("steel_bar", new ItemSteelBar());
		trapTool = registerItem("traptool", new ItemTrapTool());
		bombMaterial = registerItem("bomb_material", new ItemBombMaterial());
		flashBomb = registerItem("bomb_flash", new ItemFlashBomb());
		gaguaEgg = registerItem("egg_gagua", new ItemGaguaEgg());
		wyvernCoin = registerItem("coin", new ItemWyvernCoin());

		ingot = registerItem("ingot", new ItemIngot());
		base = registerItem("base", new ItemBase());

		kirindrops = registerItem("drop_kirin", new ItemKirin());
		tigrexdrops = registerItem("drop_tigrex", new ItemTigrex());
		rathalosdrops = registerItem("drop_rathalos", new ItemRathalos());
		deviljhodrops = registerItem("drop_deviljho", new ItemDeviljho());
		remobradrops = registerItem("drop_remobra", new ItemRemobra());

		bowgunBullet = registerItem("bullet", new ItemBullet());
		sac = registerItem("sac", new ItemSac());
		firestone = registerItem("firestone", new ItemFirestone());
		armorsphere = registerItem("armorsphere", new ItemArmorSphere());
		wyverniaClay = registerItem("clay", new ItemWyverniaClay());
		// mhfcitembullet0 = new ItemBullet(0);
		// mhfcitembullet1 = new ItemBullet(1);
		// mhfcitembullet2 = new ItemBullet(2);
		// mhfcitembullet3 = new ItemBullet(3);
		// Foods
		kirinbuff = registerItem("kirinbuff", new ItemKirinBuff());
		meat = registerItem("meat", new ItemMeats());
		nutrients = registerItem("nutrients", new ItemNutrients());

		paintball = registerItem("paintball", new ItemPaintball());

		MHFCItemFrontierSpawner = registerItem("spawner", new ItemSpawner());
		MHFCMain.logger().info("Items registered");
	}

	private WeaponData readLocalWeaponData() {
		try (
				InputStream weaponDataStream = MHFCItemRegistry.class.getResourceAsStream("weapons.json");
				Reader reader = new InputStreamReader(weaponDataStream)) {
			return WeaponData.GSON.fromJson(reader, WeaponData.class);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected io error", e);
		}
	}

	private <T extends Item> T registerItem(String registryName, WeaponData jsonWeaponData) {
		return registerItem(registryName, jsonWeaponData.configureItem(registryName));
	}

	/**
	 * Registers an item.
	 *
	 * @param registryName
	 *            the registry name of the item. It should <b>never</b> change, not even across versions.
	 * @param item
	 * @return
	 */
	private <T extends Item> T registerItem(String registryName, T item) {
		item.setRegistryName(registryName);
		item = GameRegistry.register(item);
		MHFCMain.logger().debug("Registered " + item + " with id " + item.getRegistryName());
		everyItems.add(item);
		return item;
	}

	public static MHFCItemRegistry getRegistry() {
		return serviceAccess.getService();
	}

}
