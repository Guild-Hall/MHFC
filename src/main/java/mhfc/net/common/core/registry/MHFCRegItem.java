package mhfc.net.common.core.registry;

import mhfc.net.common.helper.MHFCArmorMaterialHelper;
import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.item.ItemArmorSphere;
import mhfc.net.common.item.ItemBase;
import mhfc.net.common.item.ItemBombMaterial;
import mhfc.net.common.item.ItemFirestone;
import mhfc.net.common.item.ItemFrontierSpawner;
import mhfc.net.common.item.ItemGaguaEgg;
import mhfc.net.common.item.ItemIngot;
import mhfc.net.common.item.ItemKirin;
import mhfc.net.common.item.ItemLumberBar;
import mhfc.net.common.item.ItemMoldedIron;
import mhfc.net.common.item.ItemRathalos;
import mhfc.net.common.item.ItemSac;
import mhfc.net.common.item.ItemSteelBar;
import mhfc.net.common.item.ItemTigrex;
import mhfc.net.common.item.ItemTrapTool;
import mhfc.net.common.item.ItemWoodRig;
import mhfc.net.common.item.ItemWyvernCoin;
import mhfc.net.common.item.ItemWyverniaClay;
import mhfc.net.common.item.ItemWyverniaDust;
import mhfc.net.common.item.armor.KirinArmor;
import mhfc.net.common.item.armor.KirinSArmor;
import mhfc.net.common.item.armor.RathalosArmor;
import mhfc.net.common.item.armor.TigrexArmor;
import mhfc.net.common.item.armor.YukumoArmor;
import mhfc.net.common.item.food.ItemKirinBuff;
import mhfc.net.common.item.weapon.WeaponBHunter;
import mhfc.net.common.item.weapon.WeaponGSBone;
import mhfc.net.common.item.weapon.WeaponGSKirinThunderSword;
import mhfc.net.common.item.weapon.WeaponGSTigrex;
import mhfc.net.common.item.weapon.WeaponHHMetalBagpipe;
import mhfc.net.common.item.weapon.WeaponHKirinSpark;
import mhfc.net.common.item.weapon.WeaponHTigrex;
import mhfc.net.common.item.weapon.WeaponHWar;
import mhfc.net.common.item.weapon.WeaponHWarPlus;
import mhfc.net.common.item.weapon.WeaponHWarSlammer;
import mhfc.net.common.item.weapon.WeaponLSDarkVipern;
import mhfc.net.common.item.weapon.WeaponLSIronKatana;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegItem {
	// Weapons
	public static Item mhfcitemgsbone;
	public static Item MHFCItemGSTigrex;
	public static Item mhfcitemgskirinthundersword;
	public static Item MHFCItemHTigrex;
	public static Item mhfcitemhkirinspark;
	public static Item mhfcitemhwarhammer;
	public static Item mhfcitemhwarhammerplus;
	public static Item mhfcitemhwarslammer;
	public static Item mhfcitemlsironkatana;
	public static Item mhfcitembhunter;
	public static Item mhfcitemhhmetalbagpipe;
	public static Item mhfcitemlsdarkvipern;

	// Armors
	public static Item mhfcitemtigrexhelm;
	public static Item mhfcitemkirinhelm;
	public static Item mhfcitemtigrexchest;
	public static Item mhfcitemkirinchest;
	public static Item mhfcitemtigrexlegs;
	public static Item mhfcitemkirinlegs;
	public static Item mhfcitemtigrexboots;
	public static Item mhfcitemkirinboots;
	public static Item mhfcitemkirinShelm;
	public static Item mhfcitemkirinSchest;
	public static Item mhfcitemkirinSlegs;
	public static Item mhfcitemkirinSboots;
	public static Item mhfcitemyukumohelm;
	public static Item mhfcitemyukumochest;
	public static Item mhfcitemyukumolegs;
	public static Item mhfcitemyukumoboots;
	public static Item mhfcitemrathaloshelm;
	public static Item mhfcitemrathaloschest;
	public static Item mhfcitemrathaloslegs;
	public static Item mhfcitemrathalosboots;

	// Stuffs
	public static Item MHFCItemFrontierSpawner;
	public static Item MHFCItemTigrexScale;
	public static Item MHFCItemTigrexShell;
	public static Item mhfcitemtigrexclaw;
	public static Item mhfcitemtigrexfang;
	public static Item mhfcitemtigrexskullshell;
	public static Item mhfcitemtigrextail;
	public static Item mhfcitemkirinmane;
	public static Item mhfcitemkiringem;
	public static Item mhfcitemkirinthundertail;
	public static Item mhfcitemlightcrystal;
	public static Item mhfcitempurecrystal;
	public static Item mhfcitemplatinummane;
	public static Item mhfcitemwoodrig;
	public static Item mhfcitemlumberbar;
	public static Item mhfcitemsteelbar;
	public static Item MHFCItemWyverniaDust;
	public static Item MHFCItemTrapTool;
	public static Item MHFCItemBombMaterial;
	public static Item MHFCItemGaguaEgg;
	public static Item MHFCItemWyvernCoin;
	public static Item mhfcitemingotcarbalite;
	public static Item mhfcitemingotdragonite;
	public static Item mhfcitemingoteltalite;
	public static Item mhfcitemingotmachalite;
	public static Item mhfcitemanunium;
	public static Item mhfcitemmeganum;
	public static Item mhfcitemmoldediron;
	public static Item mhfcitemflamesac;
	public static Item mhfcitemrathalosshell;
	public static Item mhfcitemrathaloswebbing;
	public static Item mhfcitemwyvernmarrow;
	public static Item mhfcitemrathaloswing;
	public static Item mhfcitemrathalosplate;
	public static Item mhfcitemfirestone;
	public static Item mhfcitemarmorsphere;
	public static Item mhfcitemarmorsphereplus;
	public static Item mhfcitemwyverniaclay;

	// Foods
	public static Item mhfcitemkirinbuff;

	public static void init() {
		registerArmor();
		registerMeleeWeapon();
		registerItem();
		registerRangeWeapon();

		getItemID(mhfcitemlsironkatana);
		getItemID(mhfcitemlsdarkvipern);
		getItemID(mhfcitemgsbone);
		getItemID(MHFCItemGSTigrex);
		getItemID(mhfcitemgskirinthundersword);
		getItemID(mhfcitemhwarhammer);
		getItemID(mhfcitemhwarhammerplus);
		getItemID(mhfcitemhwarslammer);
		getItemID(MHFCItemHTigrex);
		getItemID(mhfcitemhkirinspark);
		getItemID(mhfcitemhhmetalbagpipe);
		getItemID(mhfcitembhunter);

		getItemID(mhfcitemyukumohelm);
		getItemID(mhfcitemrathaloshelm);
		getItemID(mhfcitemtigrexhelm);
		getItemID(mhfcitemkirinhelm);
		getItemID(mhfcitemkirinShelm);
		getItemID(mhfcitemyukumochest);
		getItemID(mhfcitemrathaloschest);
		getItemID(mhfcitemtigrexchest);
		getItemID(mhfcitemkirinchest);
		getItemID(mhfcitemkirinSchest);
		getItemID(mhfcitemyukumolegs);
		getItemID(mhfcitemrathaloslegs);
		getItemID(mhfcitemtigrexlegs);
		getItemID(mhfcitemkirinlegs);
		getItemID(mhfcitemkirinSlegs);
		getItemID(mhfcitemyukumoboots);
		getItemID(mhfcitemrathalosboots);
		getItemID(mhfcitemtigrexboots);
		getItemID(mhfcitemkirinboots);
		getItemID(mhfcitemkirinSboots);

		getItemID(MHFCItemFrontierSpawner);
		getItemID(mhfcitemrathalosshell);
		getItemID(mhfcitemrathaloswebbing);
		getItemID(mhfcitemwyvernmarrow);
		getItemID(mhfcitemrathaloswing);
		getItemID(mhfcitemrathalosplate);
		getItemID(MHFCItemTigrexScale);
		getItemID(MHFCItemTigrexShell);
		getItemID(mhfcitemtigrexclaw);
		getItemID(mhfcitemtigrexfang);
		getItemID(mhfcitemtigrexskullshell);
		getItemID(mhfcitemtigrextail);
		getItemID(mhfcitemkirinmane);
		getItemID(mhfcitemkiringem);
		getItemID(mhfcitemkirinthundertail);
		getItemID(mhfcitemlightcrystal);
		getItemID(mhfcitempurecrystal);
		getItemID(mhfcitemplatinummane);
		getItemID(mhfcitemwoodrig);
		getItemID(mhfcitemlumberbar);
		getItemID(mhfcitemsteelbar);
		getItemID(mhfcitemmoldediron);
		getItemID(mhfcitemfirestone);
		getItemID(mhfcitemwyverniaclay);
		getItemID(mhfcitemingotcarbalite);
		getItemID(mhfcitemingotdragonite);
		getItemID(mhfcitemingoteltalite);
		getItemID(mhfcitemingotmachalite);
		getItemID(mhfcitemarmorsphere);
		getItemID(mhfcitemarmorsphereplus);
		getItemID(mhfcitemanunium);
		getItemID(mhfcitemmeganum);
		getItemID(mhfcitemflamesac);
		getItemID(MHFCItemWyverniaDust);
		getItemID(MHFCItemTrapTool);
		getItemID(MHFCItemBombMaterial);
		getItemID(MHFCItemGaguaEgg);
		getItemID(MHFCItemWyvernCoin);
		getItemID(mhfcitemkirinbuff);

	}

	private static void registerArmor() {
		mhfcitemtigrexhelm = new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 0)
				.setUnlocalizedName(MHFCReference.item_tigrexhelm_name);
		mhfcitemkirinhelm = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin,
				4, 0).setUnlocalizedName(MHFCReference.item_kirinhelm_name);
		mhfcitemtigrexchest = new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 1)
				.setUnlocalizedName(MHFCReference.item_tigrexchest_name);
		mhfcitemkirinchest = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin,
				4, 1).setUnlocalizedName(MHFCReference.item_kirinchest_name);
		mhfcitemtigrexlegs = new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 2)
				.setUnlocalizedName(MHFCReference.item_tigrexlegs_name);
		mhfcitemkirinlegs = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin,
				4, 2).setUnlocalizedName(MHFCReference.item_kirinlegs_name);
		mhfcitemtigrexboots = new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 3)
				.setUnlocalizedName(MHFCReference.item_tigrexboots_name);
		mhfcitemkirinboots = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin,
				4, 3).setUnlocalizedName(MHFCReference.item_kirinboots_name);
		mhfcitemkirinShelm = new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 0)
				.setUnlocalizedName(MHFCReference.item_kirinshelm_name);
		mhfcitemkirinSchest = new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 1)
				.setUnlocalizedName(MHFCReference.item_kirinschest_name);
		mhfcitemkirinSlegs = new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 2)
				.setUnlocalizedName(MHFCReference.item_kirinslegs_name);
		mhfcitemkirinSboots = new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 3)
				.setUnlocalizedName(MHFCReference.item_kirinsboots_name);
		mhfcitemyukumohelm = new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 0)
				.setUnlocalizedName(MHFCReference.item_yukumohelm_name);
		mhfcitemyukumochest = new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 1)
				.setUnlocalizedName(MHFCReference.item_yukumochest_name);
		mhfcitemyukumolegs = new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 2)
				.setUnlocalizedName(MHFCReference.item_yukumolegs_name);
		mhfcitemyukumoboots = new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 3)
				.setUnlocalizedName(MHFCReference.item_yukumoboots_name);
		mhfcitemrathaloshelm = new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 0)
				.setUnlocalizedName(MHFCReference.item_rathaloshelm_name);
		mhfcitemrathaloschest = new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 1)
				.setUnlocalizedName(MHFCReference.item_rathaloschest_name);
		mhfcitemrathaloslegs = new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 2)
				.setUnlocalizedName(MHFCReference.item_rahaloslegs_name);
		mhfcitemrathalosboots = new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 3)
				.setUnlocalizedName(MHFCReference.item_rathalosboots_name);

	}

	private static void registerMeleeWeapon() {
		MHFCItemGSTigrex = new WeaponGSTigrex(MHFCWeaponMaterialHelper.GSTigrex);
		mhfcitemgsbone = new WeaponGSBone(MHFCWeaponMaterialHelper.GSBoneBlade);
		mhfcitemgskirinthundersword = new WeaponGSKirinThunderSword(
				MHFCWeaponMaterialHelper.GSKirin);
		MHFCItemHTigrex = new WeaponHTigrex(MHFCWeaponMaterialHelper.HTigrex);
		mhfcitemlsironkatana = new WeaponLSIronKatana(
				MHFCWeaponMaterialHelper.LSIronKatana);
		mhfcitemlsdarkvipern = new WeaponLSDarkVipern(
				MHFCWeaponMaterialHelper.LSDarkVipern);
		mhfcitemhkirinspark = new WeaponHKirinSpark(
				MHFCWeaponMaterialHelper.HKirinSpark);
		mhfcitemhwarhammer = new WeaponHWar(MHFCWeaponMaterialHelper.HWarHammer);
		mhfcitemhwarhammerplus = new WeaponHWarPlus(
				MHFCWeaponMaterialHelper.HWarHammerplus);
		mhfcitemhwarslammer = new WeaponHWarSlammer(
				MHFCWeaponMaterialHelper.HWarSlammer);
		mhfcitemhhmetalbagpipe = new WeaponHHMetalBagpipe(
				MHFCWeaponMaterialHelper.HHMetalBagpipe);
	}

	private static void registerRangeWeapon() {
		mhfcitembhunter = new WeaponBHunter();
	}
	private static void registerItem() {
		MHFCItemWyverniaDust = new ItemWyverniaDust();
		MHFCItemFrontierSpawner = new ItemFrontierSpawner();
		mhfcitemmoldediron = new ItemMoldedIron();
		mhfcitemwoodrig = new ItemWoodRig();
		mhfcitemlumberbar = new ItemLumberBar();
		mhfcitemsteelbar = new ItemSteelBar();
		MHFCItemTrapTool = new ItemTrapTool();
		MHFCItemBombMaterial = new ItemBombMaterial();
		MHFCItemGaguaEgg = new ItemGaguaEgg();
		MHFCItemWyvernCoin = new ItemWyvernCoin();
		mhfcitemingotcarbalite = new ItemIngot(0);
		mhfcitemingotdragonite = new ItemIngot(1);
		mhfcitemingoteltalite = new ItemIngot(2);
		mhfcitemingotmachalite = new ItemIngot(3);
		mhfcitemanunium = new ItemBase(0);
		mhfcitemmeganum = new ItemBase(1);
		mhfcitemkirinmane = new ItemKirin(0);
		mhfcitemkiringem = new ItemKirin(1);
		mhfcitemkirinthundertail = new ItemKirin(2);
		mhfcitemlightcrystal = new ItemKirin(3);
		mhfcitempurecrystal = new ItemKirin(4);
		mhfcitemplatinummane = new ItemKirin(5);
		MHFCItemTigrexScale = new ItemTigrex(0);
		MHFCItemTigrexShell = new ItemTigrex(1);
		mhfcitemtigrexclaw = new ItemTigrex(2);
		mhfcitemtigrexfang = new ItemTigrex(3);
		mhfcitemtigrexskullshell = new ItemTigrex(4);
		mhfcitemtigrextail = new ItemTigrex(5);
		mhfcitemflamesac = new ItemSac(0);
		mhfcitemrathalosshell = new ItemRathalos(0);
		mhfcitemrathaloswebbing = new ItemRathalos(1);
		mhfcitemwyvernmarrow = new ItemRathalos(2);
		mhfcitemrathaloswing = new ItemRathalos(3);
		mhfcitemrathalosplate = new ItemRathalos(4);
		mhfcitemfirestone = new ItemFirestone();
		mhfcitemarmorsphere = new ItemArmorSphere(0);
		mhfcitemarmorsphereplus = new ItemArmorSphere(1);
		mhfcitemwyverniaclay = new ItemWyverniaClay();

		// Foods
		mhfcitemkirinbuff = new ItemKirinBuff(6, 100, false);
	}

	private static void getItemID(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());

	}

}
