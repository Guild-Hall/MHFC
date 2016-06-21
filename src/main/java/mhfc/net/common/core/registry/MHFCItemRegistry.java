package mhfc.net.common.core.registry;

import java.util.function.Consumer;

import cpw.mods.fml.common.registry.GameRegistry;
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
import mhfc.net.common.item.armor.community.ST_Bionic;
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
import mhfc.net.common.util.lib.MHFCReference;
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
import net.minecraft.item.Item;

public class MHFCItemRegistry {

	/**
	 * Please Arrange The Weapons by its RARITY refer to the weapon package ~@Heltrato
	 */
	// Weapons

	public static int serverIDchecker;

	public static final Item weapon_gs_bone;
	public static final Item weapon_gs_tigrex;
	public static final Item weapon_gs_kirinthunders;
	public static final Item weapon_gs_berserkers;
	public static final Item weapon_gs_rathalosfire;
	public static final Item weapon_gs_deadlyserpentblade;

	public static final Item weapon_hm_tigrex;
	public static final Item weapon_hm_kirinspark;
	public static final Item weapon_hm_warhammer;
	public static final Item weapon_hm_warhammerplus;
	public static final Item weapon_hm_warslammer;
	public static final Item weapon_hm_devilsdue;
	public static final Item weapon_hm_rathalos;

	public static final Item weapon_ls_ironkatana;
	public static final Item weapon_ls_ironkatanagrace;
	public static final Item weapon_ls_ironkatanagospel;
	public static final Item weapon_ls_eagercleaver;
	public static final Item weapon_ls_devilslicer;
	public static final Item weapon_ls_truedevilslicer;
	public static final Item weapon_ls_darkvipern;
	public static final Item weapon_ls_saber;
	public static final Item weapon_ls_liondancesaber;
	public static final Item weapon_ls_lionkingsaber;
	public static final Item weapon_ls_lionkaisersaber;
	public static final Item weapon_ls_lionsroarsaber;
	public static final Item weapon_ls_miragefinsword;
	public static final Item weapon_ls_miragefinswordplus;
	public static final Item weapon_ls_phantommirage;

	public static final Item weapon_hh_metalbagpipe;
	public static final Item weapon_hh_ivoryhorn;
	public static final Item weapon_hh_tigrex;
	public static final Item weapon_hh_greatbagpipe;
	public static final Item weapon_hh_heavybagpipe;
	public static final Item weapon_hh_heavybagpipeplus;
	public static final Item weapon_hh_elitebagpipe;
	public static final Item weapon_hh_wardrums;
	public static final Item weapon_hh_wardrumsplus;
	public static final Item weapon_hh_mogwarddrums;
	public static final Item weapon_hh_blackcasket;
	public static final Item weapon_hh_darkthorntrumpet;

	public static final Item weapon_bgl_barrel;
	public static final Item weapon_bgh_rath;

	public static final Item weapon_b_hunters;
	public static final Item weapon_b_huntersstout;
	public static final Item weapon_b_huntersproud;

	// Armors
	public static final Item armor_tigrex_helm;
	public static final Item armor_tigrex_chest;
	public static final Item armor_tigrex_legs;
	public static final Item armor_tigrex_boots;

	public static final Item armor_kirin_helm;
	public static final Item armor_kirin_chest;
	public static final Item armor_kirin_legs;
	public static final Item armor_kirin_boots;

	public static final Item armor_kirinS_helm;
	public static final Item armor_kirinS_chest;
	public static final Item armor_kirinS_legs;
	public static final Item armor_kirinS_boots;

	public static final Item armor_yukumo_helm;
	public static final Item armor_yukumo_chest;
	public static final Item armor_yukumo_legs;
	public static final Item armor_yukumo_boots;

	public static final Item armor_rathalos_helm;
	public static final Item armor_rathalos_chest;
	public static final Item armor_rathalos_legs;
	public static final Item armor_rathalos_boots;

	public static final Item armor_dragoon_helm;
	public static final Item armor_dragoon_chest;
	public static final Item armor_dragoon_legs;
	public static final Item armor_dragoon_boots;

	public static final Item armor_velociprey_helm;
	public static final Item armor_velociprey_chest;
	public static final Item armor_velociprey_legs;
	public static final Item armor_velociprey_boots;

	public static final Item armor_nibelsnarf_helm;
	public static final Item armor_nibelsnarf_chest;
	public static final Item armor_nibelsnarf_legs;
	public static final Item armor_nibelsnarf_boots;

	// public static final Item armor_deviljho_helm;
	// public static final Item armor_deviljho_chest;
	// public static final Item armor_deviljho_legs;
	// public static final Item armor_deviljho_boots;

	public static final Item armor_tigrexB_helm;
	public static final Item armor_tigrexB_chest;
	public static final Item armor_tigrexB_legs;
	public static final Item armor_tigrexB_boots;

	public static final Item armor_bionic_helm;
	public static final Item armor_bionic_chest;
	public static final Item armor_bionic_legs;
	public static final Item armor_bionic_boots;

	public static final Item armor_vangis_helm;
	public static final Item armor_vangis_chest;
	public static final Item armor_vangis_legs;
	public static final Item armor_vangis_boots;

	public static final Item armor_jaggi_helm;
	public static final Item armor_jaggi_chest;
	public static final Item armor_jaggi_legs;
	public static final Item armor_jaggi_boots;

	public static final Item armor_barroth_helm;
	public static final Item armor_barroth_chest;
	public static final Item armor_barroth_legs;
	public static final Item armor_barroth_boots;

	// Materials

	public static final Item mhfcitemtigrex;
	public static final Item mhfcitemkirin;
	public static final Item mhfcitemremobra;
	// public static final Item mhfcitemlightcrystal;
	// public static final Item mhfcitempurecrystal;
	public static final Item mhfcitemrathalos;
	public static final Item mhfcitemdeviljho;
	public static final Item mhfcitembullet;

	public static final Item mhfcitemwoodrig;
	public static final Item mhfcitemlumberbar;
	public static final Item mhfcitemsteelbar;
	public static final Item MHFCItemWyverniaDust;
	public static final Item MHFCItemTrapTool;
	public static final Item MHFCItemFlashBomb;
	public static final Item MHFCItemBombMaterial;
	public static final Item MHFCItemGaguaEgg;
	public static final Item MHFCItemWyvernCoin;
	public static final Item mhfcitemarrow;

	public static final Item mhfcitemingot;
	public static final Item mhfcitembase;
	public static final Item mhfcitemarmorsphere;
	public static final Item mhfcitemsac;

	public static final Item mhfcitemmoldediron;
	public static final Item mhfcitemfirestone;
	public static final Item mhfcitemwyverniaclay;

	// Foods
	public static final Item mhfcitemkirinbuff;
	public static final Item mhfcfoodmeat;
	public static final Item mhfcfoodnutrients;

	// Spawners.(They must be Last)
	public static final Item MHFCItemFrontierSpawner;

	static {

		/*
		 * @author Heltrato: "Please sort the weapon by there Rarity.. Thanks"
		 *
		 */

		mhfcitemarrow = registerItem(new ItemWyverniaArrow());

		// Armor
		armor_yukumo_helm = registerItem(new YukumoArmor(0));
		armor_velociprey_helm = registerItem(new VelocipreyArmor(0));
		armor_jaggi_helm = registerItem(new GreatJaggiArmor(0));
		armor_rathalos_helm = registerItem(new RathalosArmor(0));
		armor_nibelsnarf_helm = registerItem(new NibelsnarfArmor(0));
		armor_barroth_helm = registerItem(new BarrothArmor(0));
		armor_tigrex_helm = registerItem(new TigrexArmor(0));
		armor_kirin_helm = registerItem(new KirinArmor(0));
		armor_vangis_helm = registerItem(new DeviljhoArmor(0));
		armor_tigrexB_helm = registerItem(new KishinArmor(0));
		armor_dragoon_helm = registerItem(new DragoonArmor(0));
		armor_kirinS_helm = registerItem(new KirinSArmor(0));
		armor_bionic_helm = registerItem(new ST_Bionic(0));

		armor_yukumo_chest = registerItem(new YukumoArmor(1));
		armor_velociprey_chest = registerItem(new VelocipreyArmor(1));
		armor_jaggi_chest = registerItem(new GreatJaggiArmor(1));
		armor_rathalos_chest = registerItem(new RathalosArmor(1));
		armor_nibelsnarf_chest = registerItem(new NibelsnarfArmor(1));
		armor_barroth_chest = registerItem(new BarrothArmor(1));
		armor_tigrex_chest = registerItem(new TigrexArmor(1));
		armor_kirin_chest = registerItem(new KirinArmor(1));
		armor_vangis_chest = registerItem(new DeviljhoArmor(1));
		armor_tigrexB_chest = registerItem(new KishinArmor(1));
		armor_dragoon_chest = registerItem(new DragoonArmor(1));
		armor_kirinS_chest = registerItem(new KirinSArmor(1));
		armor_bionic_chest = registerItem(new ST_Bionic(1));

		armor_yukumo_legs = registerItem(new YukumoArmor(2));
		armor_velociprey_legs = registerItem(new VelocipreyArmor(2));
		armor_jaggi_legs = registerItem(new GreatJaggiArmor(2));
		armor_rathalos_legs = registerItem(new RathalosArmor(2));
		armor_nibelsnarf_legs = registerItem(new NibelsnarfArmor(2));
		armor_barroth_legs = registerItem(new BarrothArmor(2));
		armor_tigrex_legs = registerItem(new TigrexArmor(2));
		armor_kirin_legs = registerItem(new KirinArmor(2));
		armor_vangis_legs = registerItem(new DeviljhoArmor(2));
		armor_tigrexB_legs = registerItem(new KishinArmor(2));
		armor_dragoon_legs = registerItem(new DragoonArmor(2));
		armor_kirinS_legs = registerItem(new KirinSArmor(2));
		armor_bionic_legs = registerItem(new ST_Bionic(2));

		armor_yukumo_boots = registerItem(new YukumoArmor(3));
		armor_velociprey_boots = registerItem(new VelocipreyArmor(3));
		armor_jaggi_boots = registerItem(new GreatJaggiArmor(3));
		armor_rathalos_boots = registerItem(new RathalosArmor(3));
		armor_nibelsnarf_boots = registerItem(new NibelsnarfArmor(3));
		armor_barroth_boots = registerItem(new BarrothArmor(3));
		armor_tigrex_boots = registerItem(new TigrexArmor(3));
		armor_kirin_boots = registerItem(new KirinArmor(3));
		armor_vangis_boots = registerItem(new DeviljhoArmor(3));
		armor_tigrexB_boots = registerItem(new KishinArmor(3));
		armor_dragoon_boots = registerItem(new DragoonArmor(3));
		armor_kirinS_boots = registerItem(new KirinSArmor(3));
		armor_bionic_boots = registerItem(new ST_Bionic(3));

		// Weapons
		// FIXME: value tuning
		weapon_gs_bone = registerGreatsword(
				b -> b.setAttack(14).setRarity(1).setName(MHFCReference.weapon_gs_bone_name));
		weapon_gs_deadlyserpentblade = registerGreatsword(
				b -> b.setAttack(35).setRarity(3).setName(MHFCReference.weapon_gs_deadlyserpentblade_name)
						.addCombatEffect(StatusEffect.Poison, 10));
		weapon_gs_tigrex = registerGreatsword(
				b -> b.setAttack(42).setRarity(4).setName(MHFCReference.weapon_gs_tigrex_name));
		weapon_gs_rathalosfire = registerGreatsword(
				b -> b.setAttack(51).setRarity(4).setName(MHFCReference.weapon_gs_rathalos_name)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_gs_kirinthunders = registerGreatsword(
				b -> b.setAttack(46).setRarity(4).setName(MHFCReference.weapon_gs_kirin_name));
		weapon_gs_berserkers = registerGreatsword(
				b -> b.setAttack(70).setRarity(6).setName(MHFCReference.weapon_gs_deviljho_name));

		weapon_ls_ironkatana = registerLongsword(
				b -> b.setAttack(18).setRarity(1).setName(MHFCReference.weapon_ls_ironkatana_name));
		weapon_ls_ironkatanagrace = registerLongsword(
				b -> b.setAttack(23).setRarity(2).setName(MHFCReference.weapon_ls_ikgrace_name));
		weapon_ls_ironkatanagospel = registerLongsword(
				b -> b.setAttack(26).setRarity(2).setName(MHFCReference.weapon_ls_ikgospel_name));
		weapon_ls_darkvipern = registerLongsword(
				b -> b.setAttack(16).setRarity(2).setName(MHFCReference.weapon_ls_darkvipern_name)
						.addCombatEffect(StatusEffect.Poison, 10));
		weapon_ls_eagercleaver = registerLongsword(
				b -> b.setAttack(32).setRarity(3).setName(MHFCReference.weapon_ls_eagercleaver_name)
						.addCombatEffect(ElementalType.Thunder, 10));
		weapon_ls_devilslicer = registerLongsword(
				b -> b.setAttack(37).setRarity(4).setName(MHFCReference.weapon_ls_devilslicer_name)
						.addCombatEffect(ElementalType.Thunder, 10));
		weapon_ls_saber = registerLongsword(
				b -> b.setAttack(31).setRarity(5).setName(MHFCReference.weapon_ls_saber_name)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_miragefinsword = registerLongsword(
				b -> b.setAttack(33).setRarity(6).setName(MHFCReference.weapon_ls_miragefinsword_name));
		weapon_ls_miragefinswordplus = registerLongsword(
				b -> b.setAttack(41).setRarity(6).setName(MHFCReference.weapon_ls_miragefinswordplus_name));
		weapon_ls_liondancesaber = registerLongsword(
				b -> b.setAttack(34).setRarity(7).setName(MHFCReference.weapon_ls_liondancesaber_name)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_truedevilslicer = registerLongsword(
				b -> b.setAttack(49).setRarity(7).setName(MHFCReference.weapon_ls_truedevilslicer_name)
						.addCombatEffect(ElementalType.Thunder, 10));
		weapon_ls_phantommirage = registerLongsword(
				b -> b.setAttack(50).setRarity(7).setName(MHFCReference.weapon_ls_phantommirage_name));
		weapon_ls_lionkingsaber = registerLongsword(
				b -> b.setAttack(39).setRarity(8).setName(MHFCReference.weapon_ls_lionkingsaber_name)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_lionkaisersaber = registerLongsword(
				b -> b.setAttack(47).setRarity(8).setName(MHFCReference.weapon_ls_lionkaisersaber_name)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_ls_lionsroarsaber = registerLongsword(
				b -> b.setAttack(59).setRarity(9).setName(MHFCReference.weapon_ls_lionsroarsaber_name)
						.addCombatEffect(ElementalType.Fire, 10));

		weapon_hm_warhammer = registerHammer(
				b -> b.setAttack(11).setRarity(1).setName(MHFCReference.weapon_hm_war_name));
		weapon_hm_warhammerplus = registerHammer(
				b -> b.setAttack(16).setRarity(1).setName(MHFCReference.weapon_hm_warplus_name));
		weapon_hm_warslammer = registerHammer(
				b -> b.setAttack(22).setRarity(2).setName(MHFCReference.weapon_hm_warslammer_name));
		weapon_hm_tigrex = registerHammer(
				b -> b.setAttack(44).setRarity(3).setName(MHFCReference.weapon_hm_tigrex_name));
		weapon_hm_rathalos = registerHammer(
				b -> b.setAttack(60).setRarity(4).setName(MHFCReference.weapon_hm_rathalos_name)
						.addCombatEffect(ElementalType.Fire, 10));
		weapon_hm_devilsdue = registerHammer(
				b -> b.setAttack(86).setRarity(7).setName(MHFCReference.weapon_hm_deviljho_name)
						.addCombatEffect(ElementalType.Dragon, 10));
		weapon_hm_kirinspark = registerHammer(
				b -> b.setAttack(63).setRarity(8).setName(MHFCReference.weapon_hm_kirin_name)
						.addCombatEffect(ElementalType.Thunder, 10));

		weapon_hh_ivoryhorn = registerHuntingHorn(
				b -> b.setAttack(5).setRarity(1).setName(MHFCReference.weapon_hh_ivoryhorn_name)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_metalbagpipe = registerHuntingHorn(
				b -> b.setAttack(8).setRarity(2).setName(MHFCReference.weapon_hh_metalbagpipe_name)
						.setNotes(Note.White, Note.Green, Note.Red));
		weapon_hh_greatbagpipe = registerHuntingHorn(
				b -> b.setAttack(12).setRarity(2).setName(MHFCReference.weapon_hh_greatbagpipe_name)
						.setNotes(Note.White, Note.Green, Note.Red));
		weapon_hh_wardrums = registerHuntingHorn(
				b -> b.setAttack(14).setRarity(2).setName(MHFCReference.weapon_hh_wardrums_name)
						.setNotes(Note.White, Note.Yellow, Note.Red));
		weapon_hh_wardrumsplus = registerHuntingHorn(
				b -> b.setAttack(18).setRarity(2).setName(MHFCReference.weapon_hh_wardrumsplus_name)
						.setNotes(Note.White, Note.Yellow, Note.Red));
		weapon_hh_heavybagpipe = registerHuntingHorn(
				b -> b.setAttack(16).setRarity(3).setName(MHFCReference.weapon_hh_heavybagpipe_name)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_heavybagpipeplus = registerHuntingHorn(
				b -> b.setAttack(19).setRarity(3).setName(MHFCReference.weapon_hh_heavybagpipeplus_name)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_tigrex = registerHuntingHorn(
				b -> b.setAttack(32).setRarity(5).setName(MHFCReference.weapon_hh_tigrex_name)
						.setNotes(Note.Purple, Note.Blue, Note.Red));
		weapon_hh_mogwarddrums = registerHuntingHorn(
				b -> b.setAttack(35).setRarity(5).setName(MHFCReference.weapon_hh_mogwarddrums_name)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_elitebagpipe = registerHuntingHorn(
				b -> b.setAttack(25).setRarity(6).setName(MHFCReference.weapon_hh_elitebagpipe_name)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_darkthorntrumpet = registerHuntingHorn(
				b -> b.setAttack(71).setRarity(9).setName(MHFCReference.weapon_hh_darkthorntrumpet_name)
						.setNotes(Note.White, Note.Blue, Note.Red));
		weapon_hh_blackcasket = registerHuntingHorn(
				b -> b.setAttack(84).setRarity(10).setName(MHFCReference.weapon_hh_blackcasket_name)
						.setNotes(Note.White, Note.Blue, Note.Red));

		// Range weapons

		weapon_b_hunters = registerBow(b -> b.setAttack(8).setRarity(1).setName(MHFCReference.weapon_bow_hunters_name));
		weapon_b_huntersstout = registerBow(
				b -> b.setAttack(15).setRarity(2).setName(MHFCReference.weapon_bow_huntersstout_name));
		weapon_b_huntersproud = registerBow(
				b -> b.setAttack(15).setRarity(3).setName(MHFCReference.weapon_bow_huntersproud_name)
						.addCombatEffect(ElementalType.Ice, 3));

		weapon_bgl_barrel = registerLightBowgun(
				b -> b.setAttack(12).setRarity(1).setName(MHFCReference.weapon_bgl_barrel_name));

		weapon_bgh_rath = registerHeavyBowgun(
				b -> b.setAttack(40).setRarity(1).setName(MHFCReference.weapon_bgl_spartacusfire_name));
		// Items... drops
		MHFCItemWyverniaDust = registerItem(new ItemWyverniaDust());

		mhfcitemmoldediron = registerItem(new ItemMoldedIron());
		mhfcitemwoodrig = registerItem(new ItemWoodRig());
		mhfcitemlumberbar = registerItem(new ItemLumberBar());
		mhfcitemsteelbar = registerItem(new ItemSteelBar());
		MHFCItemTrapTool = registerItem(new ItemTrapTool());
		MHFCItemBombMaterial = registerItem(new ItemBombMaterial());
		MHFCItemFlashBomb = registerItem(new ItemFlashBomb());
		MHFCItemGaguaEgg = registerItem(new ItemGaguaEgg());
		MHFCItemWyvernCoin = registerItem(new ItemWyvernCoin());

		mhfcitemingot = registerItem(new ItemIngot());
		mhfcitembase = registerItem(new ItemBase());

		mhfcitemkirin = registerItem(new ItemKirin());
		mhfcitemtigrex = registerItem(new ItemTigrex());
		mhfcitemrathalos = registerItem(new ItemRathalos());
		mhfcitemdeviljho = registerItem(new ItemDeviljho());
		mhfcitemremobra = registerItem(new ItemRemobra());

		mhfcitembullet = registerItem(new ItemBullet());
		mhfcitemsac = registerItem(new ItemSac());
		mhfcitemfirestone = registerItem(new ItemFirestone());
		mhfcitemarmorsphere = registerItem(new ItemArmorSphere());
		mhfcitemwyverniaclay = registerItem(new ItemWyverniaClay());
		// mhfcitembullet0 = new ItemBullet(0);
		// mhfcitembullet1 = new ItemBullet(1);
		// mhfcitembullet2 = new ItemBullet(2);
		// mhfcitembullet3 = new ItemBullet(3);
		// Foods
		mhfcitemkirinbuff = registerItem(new ItemKirinBuff());
		mhfcfoodmeat = registerItem(new ItemMeats());
		mhfcfoodnutrients = registerItem(new ItemNutrients());

		MHFCItemFrontierSpawner = registerItem(new ItemSpawner());
	}

	public static void init() {}

	private static ItemGreatsword registerGreatsword(Consumer<GreatswordWeaponStatsBuilder> config) {
		return registerItem(ItemGreatsword.build(config));
	}

	private static ItemLongsword registerLongsword(Consumer<LongswordWeaponStatsBuilder> config) {
		return registerItem(ItemLongsword.build(config));
	}

	private static ItemHammer registerHammer(Consumer<HammerWeaponStatsBuilder> config) {
		return registerItem(ItemHammer.build(config));
	}

	private static ItemHuntingHorn registerHuntingHorn(Consumer<HuntingHornWeaponStatsBuilder> config) {
		return registerItem(ItemHuntingHorn.build(config));
	}

	private static ItemBow registerBow(Consumer<BowWeaponStatsBuilder> config) {
		return registerItem(ItemBow.build(config));
	}

	private static ItemLightBowgun registerLightBowgun(Consumer<BowgunWeaponStatsBuilder> config) {
		return registerItem(ItemLightBowgun.build(config));
	}

	private static ItemHeavyBowgun registerHeavyBowgun(Consumer<BowgunWeaponStatsBuilder> config) {
		return registerItem(ItemHeavyBowgun.build(config));
	}

	private static <T extends Item> T registerItem(T item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());
		return item;
	}

}
