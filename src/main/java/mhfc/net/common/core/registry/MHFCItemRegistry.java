package mhfc.net.common.core.registry;

import java.util.function.Consumer;

import mhfc.net.MHFCMain;
import mhfc.net.common.item.armor.BarrothArmor;
import mhfc.net.common.item.armor.DeviljhoArmor;
import mhfc.net.common.item.armor.DragoonArmor;
import mhfc.net.common.item.armor.GreatJaggiArmor;
import mhfc.net.common.item.armor.KirinArmor;
import mhfc.net.common.item.armor.KirinSArmor;
import mhfc.net.common.item.armor.KishinArmor;
import mhfc.net.common.item.armor.NibelsnarfArmor;
import mhfc.net.common.item.armor.RathalosArmor;
import mhfc.net.common.item.armor.TigrexArmor;
import mhfc.net.common.item.armor.VelocipreyArmor;
import mhfc.net.common.item.armor.YukumoArmor;
import mhfc.net.common.item.armor.community.ST_BionicArmor;
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
import mhfc.net.common.item.materials.ItemWyverniaDust;
import mhfc.net.common.item.tools.ItemPaintball;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.services.IServiceKey;
import mhfc.net.common.weapon.melee.greatsword.GreatswordWeaponStats.GreatswordWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.greatsword.ItemGreatsword;
import mhfc.net.common.weapon.melee.hammer.HammerWeaponStats.HammerWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.hammer.ItemHammer;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats.HuntingHornWeaponStatsBuilder;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import mhfc.net.common.weapon.melee.huntinghorn.Note;
import mhfc.net.common.weapon.melee.longsword.ItemLongsword;
import mhfc.net.common.weapon.melee.longsword.LongswordWeaponStats.LongswordWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bow.BowWeaponStats.BowWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bow.ItemBow;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats.BowgunWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bowgun.heavy.ItemHeavyBowgun;
import mhfc.net.common.weapon.range.bowgun.light.ItemLightBowgun;
import mhfc.net.common.weapon.stats.ElementalType;
import mhfc.net.common.weapon.stats.StatusEffect;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCItemRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCItemRegistry> serviceAccess = RegistryWrapper
			.registerService("item registry", MHFCItemRegistry::new, MHFCMain.initPhase);

	/**
	 * Please Arrange The Weapons by its RARITY refer to the weapon package ~@Heltrato
	 */
	// Weapons

	public final Item weapon_gs_bone;
	public final Item weapon_gs_tigrex;
	public final Item weapon_gs_kirinthunders;
	public final Item weapon_gs_berserkers;
	public final Item weapon_gs_rathalosfire;
	public final Item weapon_gs_deadlyserpentblade;

	public final Item weapon_hm_tigrex;
	public final Item weapon_hm_kirinspark;
	public final Item weapon_hm_warhammer;
	public final Item weapon_hm_warhammerplus;
	public final Item weapon_hm_warslammer;
	public final Item weapon_hm_devilsdue;
	public final Item weapon_hm_rathalos;

	public final Item weapon_ls_ironkatana;
	public final Item weapon_ls_ironkatanagrace;
	public final Item weapon_ls_ironkatanagospel;
	public final Item weapon_ls_eagercleaver;
	public final Item weapon_ls_devilslicer;
	public final Item weapon_ls_truedevilslicer;
	public final Item weapon_ls_darkvipern;
	public final Item weapon_ls_saber;
	public final Item weapon_ls_liondancesaber;
	public final Item weapon_ls_lionkingsaber;
	public final Item weapon_ls_lionkaisersaber;
	public final Item weapon_ls_lionsroarsaber;
	public final Item weapon_ls_miragefinsword;
	public final Item weapon_ls_miragefinswordplus;
	public final Item weapon_ls_phantommirage;

	public final Item weapon_hh_metalbagpipe;
	public final Item weapon_hh_ivoryhorn;
	public final Item weapon_hh_tigrex;
	public final Item weapon_hh_greatbagpipe;
	public final Item weapon_hh_heavybagpipe;
	public final Item weapon_hh_heavybagpipeplus;
	public final Item weapon_hh_elitebagpipe;
	public final Item weapon_hh_wardrums;
	public final Item weapon_hh_wardrumsplus;
	public final Item weapon_hh_mogwarddrums;
	public final Item weapon_hh_blackcasket;
	public final Item weapon_hh_darkthorntrumpet;

	public final Item weapon_bgl_barrel;
	public final Item weapon_bgh_rath;

	public final Item weapon_b_hunters;
	public final Item weapon_b_huntersstout;
	public final Item weapon_b_huntersproud;

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

	// public  final Item armor_deviljho_helm;
	// public  final Item armor_deviljho_chest;
	// public  final Item armor_deviljho_legs;
	// public  final Item armor_deviljho_boots;

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

	public final Item tigrexdrops;
	public final Item kirindrops;
	public final Item remobradrops;
	// public  final Item mhfcitemlightcrystal;
	// public  final Item mhfcitempurecrystal;
	public final Item rathalosdrops;
	public final Item deviljhodrops;
	public final Item bowgunBullet;

	public final Item woodrig;
	public final Item lumberbar;
	public final Item steelbar;
	public final Item wyverniaDust;
	public final Item trapTool;
	public final Item flashBomb;
	public final Item bombMaterial;
	public final Item gaguaEgg;
	public final Item wyvernCoin;
	public final Item paintball;
	public final Item arrow;

	public final Item ingot;
	public final Item base;
	public final Item armorsphere;
	public final Item itemsac;

	public final Item moldedIron;
	public final Item firestone;
	public final Item wyverniaClay;

	// Foods
	public final Item kirinbuff;
	public final Item meat;
	public final Item nutrients;

	// Spawners.(They must be Last)
	public final Item MHFCItemFrontierSpawner;

	private MHFCItemRegistry() {
		/*
		 * @author Heltrato: "Please sort the weapon by there Rarity.. Thanks"
		 *
		 */

		arrow = registerItem(new ItemWyverniaArrow());

		// Armor
		armor_yukumo_helm = registerItem(new YukumoArmor(EntityEquipmentSlot.HEAD));
		armor_velociprey_helm = registerItem(new VelocipreyArmor(EntityEquipmentSlot.HEAD));
		armor_jaggi_helm = registerItem(new GreatJaggiArmor(EntityEquipmentSlot.HEAD));
		armor_rathalos_helm = registerItem(new RathalosArmor(EntityEquipmentSlot.HEAD));
		armor_nibelsnarf_helm = registerItem(new NibelsnarfArmor(EntityEquipmentSlot.HEAD));
		armor_barroth_helm = registerItem(new BarrothArmor(EntityEquipmentSlot.HEAD));
		armor_tigrex_helm = registerItem(new TigrexArmor(EntityEquipmentSlot.HEAD));
		armor_kirin_helm = registerItem(new KirinArmor(EntityEquipmentSlot.HEAD));
		armor_vangis_helm = registerItem(new DeviljhoArmor(EntityEquipmentSlot.HEAD));
		armor_tigrexB_helm = registerItem(new KishinArmor(EntityEquipmentSlot.HEAD));
		armor_dragoon_helm = registerItem(new DragoonArmor(EntityEquipmentSlot.HEAD));
		armor_kirinS_helm = registerItem(new KirinSArmor(EntityEquipmentSlot.HEAD));
		armor_bionic_helm = registerItem(new ST_BionicArmor(EntityEquipmentSlot.HEAD));

		armor_yukumo_chest = registerItem(new YukumoArmor(EntityEquipmentSlot.CHEST));
		armor_velociprey_chest = registerItem(new VelocipreyArmor(EntityEquipmentSlot.CHEST));
		armor_jaggi_chest = registerItem(new GreatJaggiArmor(EntityEquipmentSlot.CHEST));
		armor_rathalos_chest = registerItem(new RathalosArmor(EntityEquipmentSlot.CHEST));
		armor_nibelsnarf_chest = registerItem(new NibelsnarfArmor(EntityEquipmentSlot.CHEST));
		armor_barroth_chest = registerItem(new BarrothArmor(EntityEquipmentSlot.CHEST));
		armor_tigrex_chest = registerItem(new TigrexArmor(EntityEquipmentSlot.CHEST));
		armor_kirin_chest = registerItem(new KirinArmor(EntityEquipmentSlot.CHEST));
		armor_vangis_chest = registerItem(new DeviljhoArmor(EntityEquipmentSlot.CHEST));
		armor_tigrexB_chest = registerItem(new KishinArmor(EntityEquipmentSlot.CHEST));
		armor_dragoon_chest = registerItem(new DragoonArmor(EntityEquipmentSlot.CHEST));
		armor_kirinS_chest = registerItem(new KirinSArmor(EntityEquipmentSlot.CHEST));
		armor_bionic_chest = registerItem(new ST_BionicArmor(EntityEquipmentSlot.CHEST));

		armor_yukumo_legs = registerItem(new YukumoArmor(EntityEquipmentSlot.LEGS));
		armor_velociprey_legs = registerItem(new VelocipreyArmor(EntityEquipmentSlot.LEGS));
		armor_jaggi_legs = registerItem(new GreatJaggiArmor(EntityEquipmentSlot.LEGS));
		armor_rathalos_legs = registerItem(new RathalosArmor(EntityEquipmentSlot.LEGS));
		armor_nibelsnarf_legs = registerItem(new NibelsnarfArmor(EntityEquipmentSlot.LEGS));
		armor_barroth_legs = registerItem(new BarrothArmor(EntityEquipmentSlot.LEGS));
		armor_tigrex_legs = registerItem(new TigrexArmor(EntityEquipmentSlot.LEGS));
		armor_kirin_legs = registerItem(new KirinArmor(EntityEquipmentSlot.LEGS));
		armor_vangis_legs = registerItem(new DeviljhoArmor(EntityEquipmentSlot.LEGS));
		armor_tigrexB_legs = registerItem(new KishinArmor(EntityEquipmentSlot.LEGS));
		armor_dragoon_legs = registerItem(new DragoonArmor(EntityEquipmentSlot.LEGS));
		armor_kirinS_legs = registerItem(new KirinSArmor(EntityEquipmentSlot.LEGS));
		armor_bionic_legs = registerItem(new ST_BionicArmor(EntityEquipmentSlot.LEGS));

		armor_yukumo_boots = registerItem(new YukumoArmor(EntityEquipmentSlot.FEET));
		armor_velociprey_boots = registerItem(new VelocipreyArmor(EntityEquipmentSlot.FEET));
		armor_jaggi_boots = registerItem(new GreatJaggiArmor(EntityEquipmentSlot.FEET));
		armor_rathalos_boots = registerItem(new RathalosArmor(EntityEquipmentSlot.FEET));
		armor_nibelsnarf_boots = registerItem(new NibelsnarfArmor(EntityEquipmentSlot.FEET));
		armor_barroth_boots = registerItem(new BarrothArmor(EntityEquipmentSlot.FEET));
		armor_tigrex_boots = registerItem(new TigrexArmor(EntityEquipmentSlot.FEET));
		armor_kirin_boots = registerItem(new KirinArmor(EntityEquipmentSlot.FEET));
		armor_vangis_boots = registerItem(new DeviljhoArmor(EntityEquipmentSlot.FEET));
		armor_tigrexB_boots = registerItem(new KishinArmor(EntityEquipmentSlot.FEET));
		armor_dragoon_boots = registerItem(new DragoonArmor(EntityEquipmentSlot.FEET));
		armor_kirinS_boots = registerItem(new KirinSArmor(EntityEquipmentSlot.FEET));
		armor_bionic_boots = registerItem(new ST_BionicArmor(EntityEquipmentSlot.FEET));

		// Weapons
		// FIXME: value tuning
		weapon_gs_bone = registerGreatsword(
				"gs_bone",
				b -> b.setName(MHFCReference.weapon_gs_bone_name).setAttack(26).setRarity(1));
		weapon_gs_deadlyserpentblade = registerGreatsword(
				"gs_deadly_serpent",
				b -> b.setName(MHFCReference.weapon_gs_deadlyserpentblade_name).setAttack(35).setRarity(3)
						.addCombatEffect(StatusEffect.Poison, 10));
		weapon_gs_tigrex = registerGreatsword(
				"gs_tigrex",
				b -> b.setName(MHFCReference.weapon_gs_tigrex_name).setAttack(46).setRarity(4));
		weapon_gs_rathalosfire = registerGreatsword(
				"gs_rathalos",
				b -> b.setName(MHFCReference.weapon_gs_rathalos_name).setAttack(58).setRarity(4)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_gs_kirinthunders = registerGreatsword(
				"gs_kirin",
				b -> b.setName(MHFCReference.weapon_gs_kirin_name).setAttack(61).setRarity(4));
		weapon_gs_berserkers = registerGreatsword(
				"gs_berserker",
				b -> b.setName(MHFCReference.weapon_gs_deviljho_name).setAttack(88).setRarity(6));

		weapon_ls_ironkatana = registerLongsword(
				"ls_iron",
				b -> b.setName(MHFCReference.weapon_ls_ironkatana_name).setAttack(23).setRarity(1));
		weapon_ls_ironkatanagrace = registerLongsword(
				"ls_iron_grace",
				b -> b.setName(MHFCReference.weapon_ls_ikgrace_name).setAttack(26).setRarity(2));
		weapon_ls_ironkatanagospel = registerLongsword(
				"ls_iron_gospel",
				b -> b.setName(MHFCReference.weapon_ls_ikgospel_name).setAttack(29).setRarity(2));
		weapon_ls_darkvipern = registerLongsword(
				"ls_darkviper",
				b -> b.setName(MHFCReference.weapon_ls_darkvipern_name).setAttack(21).setRarity(2)
						.addCombatEffect(StatusEffect.Poison, 10));
		weapon_ls_eagercleaver = registerLongsword(
				"ls_eagercleaver",
				b -> b.setName(MHFCReference.weapon_ls_eagercleaver_name).setAttack(40).setRarity(3)
						.addCombatEffect(ElementalType.Thunder, 10));
		weapon_ls_devilslicer = registerLongsword(
				"ls_devilslicer",
				b -> b.setName(MHFCReference.weapon_ls_devilslicer_name).setAttack(46).setRarity(4)
						.addCombatEffect(ElementalType.Thunder, 10));
		weapon_ls_saber = registerLongsword(
				"ls_saber",
				b -> b.setName(MHFCReference.weapon_ls_saber_name).setAttack(30).setRarity(5)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_miragefinsword = registerLongsword(
				"ls_mirage",
				b -> b.setName(MHFCReference.weapon_ls_miragefinsword_name).setAttack(51).setRarity(6));
		weapon_ls_miragefinswordplus = registerLongsword(
				"ls_mirage_plus",
				b -> b.setName(MHFCReference.weapon_ls_miragefinswordplus_name).setAttack(58).setRarity(6));
		weapon_ls_liondancesaber = registerLongsword(
				"ls_lion_dance",
				b -> b.setName(MHFCReference.weapon_ls_liondancesaber_name).setAttack(49).setRarity(7)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_truedevilslicer = registerLongsword(
				"ls_devilslider_true",
				b -> b.setName(MHFCReference.weapon_ls_truedevilslicer_name).setAttack(57).setRarity(7)
						.addCombatEffect(ElementalType.Thunder, 10));
		weapon_ls_phantommirage = registerLongsword(
				"ls_mirage_phantom",
				b -> b.setName(MHFCReference.weapon_ls_phantommirage_name).setAttack(67).setRarity(7));
		weapon_ls_lionkingsaber = registerLongsword(
				"ls_lion_king",
				b -> b.setName(MHFCReference.weapon_ls_lionkingsaber_name).setAttack(65).setRarity(8)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_lionkaisersaber = registerLongsword(
				"ls_lion_kaiser",
				b -> b.setName(MHFCReference.weapon_ls_lionkaisersaber_name).setAttack(74).setRarity(8)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_lionsroarsaber = registerLongsword(
				"ls_lion_roar",
				b -> b.setName(MHFCReference.weapon_ls_lionsroarsaber_name).setAttack(81).setRarity(9)
						.addCombatEffect(ElementalType.Fire, 10));

		weapon_hm_warhammer = registerHammer(
				"hm_war",
				b -> b.setName(MHFCReference.weapon_hm_war_name).setAttack(20).setRarity(1));
		weapon_hm_warhammerplus = registerHammer(
				"hm_war_plus",
				b -> b.setName(MHFCReference.weapon_hm_warplus_name).setAttack(24).setRarity(1));
		weapon_hm_warslammer = registerHammer(
				"hm_war_slammer",
				b -> b.setName(MHFCReference.weapon_hm_warslammer_name).setAttack(30).setRarity(2));
		weapon_hm_tigrex = registerHammer(
				"hm_tigrex",
				b -> b.setName(MHFCReference.weapon_hm_tigrex_name).setAttack(47).setRarity(3));
		weapon_hm_rathalos = registerHammer(
				"hm_rathalos",
				b -> b.setName(MHFCReference.weapon_hm_rathalos_name).setAttack(42).setRarity(4)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_hm_devilsdue = registerHammer(
				"hm_devil",
				b -> b.setName(MHFCReference.weapon_hm_deviljho_name).setAttack(80).setRarity(7)
						.addCombatEffect(ElementalType.Dragon, 10));
		weapon_hm_kirinspark = registerHammer(
				"hm_kirin_spark",
				b -> b.setName(MHFCReference.weapon_hm_kirin_name).setAttack(89).setRarity(8)
						.addCombatEffect(ElementalType.Thunder, 10));

		weapon_hh_ivoryhorn = registerHuntingHorn(
				"hh_ivory",
				b -> b.setName(MHFCReference.weapon_hh_ivoryhorn_name).setAttack(21).setRarity(1)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_metalbagpipe = registerHuntingHorn(
				"hh_bagpipe",
				b -> b.setName(MHFCReference.weapon_hh_metalbagpipe_name).setAttack(26).setRarity(2)
						.setNotes(Note.White, Note.Green, Note.Red));
		weapon_hh_greatbagpipe = registerHuntingHorn(
				"hh_bagpipe_great",
				b -> b.setName(MHFCReference.weapon_hh_greatbagpipe_name).setAttack(35).setRarity(2)
						.setNotes(Note.White, Note.Green, Note.Red));
		weapon_hh_wardrums = registerHuntingHorn(
				"hh_wardrums",
				b -> b.setName(MHFCReference.weapon_hh_wardrums_name).setAttack(25).setRarity(2)
						.setNotes(Note.White, Note.Yellow, Note.Red));
		weapon_hh_wardrumsplus = registerHuntingHorn(
				"hh_wardrums_plus",
				b -> b.setName(MHFCReference.weapon_hh_wardrumsplus_name).setAttack(31).setRarity(2)
						.setNotes(Note.White, Note.Yellow, Note.Red));
		weapon_hh_heavybagpipe = registerHuntingHorn(
				"hh_bagpipe_heavy",
				b -> b.setName(MHFCReference.weapon_hh_heavybagpipe_name).setAttack(42).setRarity(3)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_heavybagpipeplus = registerHuntingHorn(
				"hh_bagpipe_heavy_plus",
				b -> b.setName(MHFCReference.weapon_hh_heavybagpipeplus_name).setAttack(47).setRarity(3)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_tigrex = registerHuntingHorn(
				"hh_tigrex",
				b -> b.setName(MHFCReference.weapon_hh_tigrex_name).setAttack(55).setRarity(5)
						.setNotes(Note.Purple, Note.Blue, Note.Red));
		weapon_hh_mogwarddrums = registerHuntingHorn(
				"hh_wardrums_bongo",
				b -> b.setName(MHFCReference.weapon_hh_mogwarddrums_name).setAttack(72).setRarity(5)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_elitebagpipe = registerHuntingHorn(
				"hh_bigpipe_elite",
				b -> b.setName(MHFCReference.weapon_hh_elitebagpipe_name).setAttack(60).setRarity(6)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_darkthorntrumpet = registerHuntingHorn(
				"hh_darkthorn",
				b -> b.setName(MHFCReference.weapon_hh_darkthorntrumpet_name).setAttack(84).setRarity(9)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_blackcasket = registerHuntingHorn(
				"hh_black_casket",
				b -> b.setName(MHFCReference.weapon_hh_blackcasket_name).setAttack(91).setRarity(10)
						.setNotes(Note.White, Note.Blue, Note.Red));

		// Range weapons

		weapon_b_hunters = registerBow(
				"b_hunter",
				b -> b.setName(MHFCReference.weapon_bow_hunters_name).setAttack(8).setRarity(1));
		weapon_b_huntersstout = registerBow(
				"b_hunter_stout",
				b -> b.setName(MHFCReference.weapon_bow_huntersstout_name).setAttack(25).setRarity(2));
		weapon_b_huntersproud = registerBow(
				"b_hunter_proud",
				b -> b.setName(MHFCReference.weapon_bow_huntersproud_name).setAttack(30).setRarity(3)
						.addCombatEffect(ElementalType.Ice, 3));

		weapon_bgl_barrel = registerLightBowgun(
				"bgl_barrel",
				b -> b.setName(MHFCReference.weapon_bgl_barrel_name).setAttack(12).setRarity(1));

		weapon_bgh_rath = registerHeavyBowgun(
				"bgh_spartacus",
				b -> b.setName(MHFCReference.weapon_bgl_spartacusfire_name).setAttack(40).setRarity(1));
		// Items... drops
		wyverniaDust = registerItem(new ItemWyverniaDust());

		moldedIron = registerItem(new ItemMoldedIron());
		woodrig = registerItem(new ItemWoodRig());
		lumberbar = registerItem(new ItemLumberBar());
		steelbar = registerItem(new ItemSteelBar());
		trapTool = registerItem(new ItemTrapTool());
		bombMaterial = registerItem(new ItemBombMaterial());
		flashBomb = registerItem(new ItemFlashBomb());
		gaguaEgg = registerItem(new ItemGaguaEgg());
		wyvernCoin = registerItem(new ItemWyvernCoin());

		ingot = registerItem(new ItemIngot());
		base = registerItem(new ItemBase());

		kirindrops = registerItem(new ItemKirin());
		tigrexdrops = registerItem(new ItemTigrex());
		rathalosdrops = registerItem(new ItemRathalos());
		deviljhodrops = registerItem(new ItemDeviljho());
		remobradrops = registerItem(new ItemRemobra());

		bowgunBullet = registerItem(new ItemBullet());
		itemsac = registerItem(new ItemSac());
		firestone = registerItem(new ItemFirestone());
		armorsphere = registerItem(new ItemArmorSphere());
		wyverniaClay = registerItem(new ItemWyverniaClay());
		// mhfcitembullet0 = new ItemBullet(0);
		// mhfcitembullet1 = new ItemBullet(1);
		// mhfcitembullet2 = new ItemBullet(2);
		// mhfcitembullet3 = new ItemBullet(3);
		// Foods
		kirinbuff = registerItem(new ItemKirinBuff());
		meat = registerItem(new ItemMeats());
		nutrients = registerItem(new ItemNutrients());

		paintball = registerItem(new ItemPaintball());

		MHFCItemFrontierSpawner = registerItem(new ItemSpawner());
		MHFCMain.logger().info("Items registered");
	}

	private static ItemGreatsword registerGreatsword(String name, Consumer<GreatswordWeaponStatsBuilder> config) {
		return registerItem(name, ItemGreatsword.build(config));
	}

	private static ItemLongsword registerLongsword(String name, Consumer<LongswordWeaponStatsBuilder> config) {
		return registerItem(name, ItemLongsword.build(config));
	}

	private static ItemHammer registerHammer(String name, Consumer<HammerWeaponStatsBuilder> config) {
		return registerItem(name, ItemHammer.build(config));
	}

	private static ItemHuntingHorn registerHuntingHorn(String name, Consumer<HuntingHornWeaponStatsBuilder> config) {
		return registerItem(name, ItemHuntingHorn.build(config));
	}

	private static ItemBow registerBow(String name, Consumer<BowWeaponStatsBuilder> config) {
		return registerItem(name, ItemBow.build(config));
	}

	private static ItemLightBowgun registerLightBowgun(String name, Consumer<BowgunWeaponStatsBuilder> config) {
		return registerItem(name, ItemLightBowgun.build(config));
	}

	private static ItemHeavyBowgun registerHeavyBowgun(String name, Consumer<BowgunWeaponStatsBuilder> config) {
		return registerItem(name, ItemHeavyBowgun.build(config));
	}

	@Deprecated
	private static <T extends Item> T registerItem(T item) {
		return registerItem(item.getUnlocalizedName(), item);
	}

	/**
	 * Registers an item.
	 *
	 * @param registryName
	 *            the registry name of the item. It should <b>never</b> change, not even across versions.
	 * @param item
	 * @return
	 */
	private static <T extends Item> T registerItem(String registryName, T item) {
		item.setRegistryName(registryName);
		return GameRegistry.register(item);
	}

	public static MHFCItemRegistry getRegistry() {
		return serviceAccess.getService();
	}

}
