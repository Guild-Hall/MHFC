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

public class MHFCItemRegistry {
	// Weapons
	public static final Item mhfcitemgsbone;
	public static final Item MHFCItemGSTigrex;
	public static final Item mhfcitemgskirinthundersword;
	public static final Item MHFCItemHTigrex;
	public static final Item mhfcitemhkirinspark;
	public static final Item mhfcitemhwarhammer;
	public static final Item mhfcitemhwarhammerplus;
	public static final Item mhfcitemhwarslammer;
	public static final Item mhfcitemlsironkatana;
	public static final Item mhfcitembhunter;
	public static final Item mhfcitemhhmetalbagpipe;
	public static final Item mhfcitemlsdarkvipern;

	// Armors
	public static final Item mhfcitemtigrexhelm;
	public static final Item mhfcitemkirinhelm;
	public static final Item mhfcitemtigrexchest;
	public static final Item mhfcitemkirinchest;
	public static final Item mhfcitemtigrexlegs;
	public static final Item mhfcitemkirinlegs;
	public static final Item mhfcitemtigrexboots;
	public static final Item mhfcitemkirinboots;
	public static final Item mhfcitemkirinShelm;
	public static final Item mhfcitemkirinSchest;
	public static final Item mhfcitemkirinSlegs;
	public static final Item mhfcitemkirinSboots;
	public static final Item mhfcitemyukumohelm;
	public static final Item mhfcitemyukumochest;
	public static final Item mhfcitemyukumolegs;
	public static final Item mhfcitemyukumoboots;
	public static final Item mhfcitemrathaloshelm;
	public static final Item mhfcitemrathaloschest;
	public static final Item mhfcitemrathaloslegs;
	public static final Item mhfcitemrathalosboots;

	// Stuffs
	public static final Item MHFCItemFrontierSpawner;
	public static final Item MHFCItemTigrexScale;
	public static final Item MHFCItemTigrexShell;
	public static final Item mhfcitemtigrexclaw;
	public static final Item mhfcitemtigrexfang;
	public static final Item mhfcitemtigrexskullshell;
	public static final Item mhfcitemtigrextail;
	public static final Item mhfcitemkirinmane;
	public static final Item mhfcitemkiringem;
	public static final Item mhfcitemkirinthundertail;
	public static final Item mhfcitemlightcrystal;
	public static final Item mhfcitempurecrystal;
	public static final Item mhfcitemplatinummane;
	public static final Item mhfcitemwoodrig;
	public static final Item mhfcitemlumberbar;
	public static final Item mhfcitemsteelbar;
	public static final Item MHFCItemWyverniaDust;
	public static final Item MHFCItemTrapTool;
	public static final Item MHFCItemBombMaterial;
	public static final Item MHFCItemGaguaEgg;
	public static final Item MHFCItemWyvernCoin;
	public static final Item mhfcitemingotcarbalite;
	public static final Item mhfcitemingotdragonite;
	public static final Item mhfcitemingoteltalite;
	public static final Item mhfcitemingotmachalite;
	public static final Item mhfcitemanunium;
	public static final Item mhfcitemmeganum;
	public static final Item mhfcitemmoldediron;
	public static final Item mhfcitemflamesac;
	public static final Item mhfcitemrathalosshell;
	public static final Item mhfcitemrathaloswebbing;
	public static final Item mhfcitemwyvernmarrow;
	public static final Item mhfcitemrathaloswing;
	public static final Item mhfcitemrathalosplate;
	public static final Item mhfcitemfirestone;
	public static final Item mhfcitemarmorsphere;
	public static final Item mhfcitemarmorsphereplus;
	public static final Item mhfcitemwyverniaclay;

	// Foods
	public static final Item mhfcitemkirinbuff;

	static {
		// Armor
		mhfcitemtigrexhelm = registerItem(new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 0)
				.setUnlocalizedName(MHFCReference.armor_tigrex_helm_name));
		mhfcitemkirinhelm = registerItem(new KirinArmor(
				MHFCArmorMaterialHelper.ArmorKirin, 4, 0)
				.setUnlocalizedName(MHFCReference.armor_kirin_helm_name));
		mhfcitemtigrexchest = registerItem(new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 1)
				.setUnlocalizedName(MHFCReference.armor_tigrex_chest_name));
		mhfcitemkirinchest = registerItem(new KirinArmor(
				MHFCArmorMaterialHelper.ArmorKirin, 4, 1)
				.setUnlocalizedName(MHFCReference.armor_kirin_chest_name));
		mhfcitemtigrexlegs = registerItem(new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 2)
				.setUnlocalizedName(MHFCReference.armor_tigrex_legs_name));
		mhfcitemkirinlegs = registerItem(new KirinArmor(
				MHFCArmorMaterialHelper.ArmorKirin, 4, 2)
				.setUnlocalizedName(MHFCReference.armor_kirin_legs_name));
		mhfcitemtigrexboots = registerItem(new TigrexArmor(
				MHFCArmorMaterialHelper.ArmorTigrex, 4, 3)
				.setUnlocalizedName(MHFCReference.armor_tigrex_boots_name));
		mhfcitemkirinboots = registerItem(new KirinArmor(
				MHFCArmorMaterialHelper.ArmorKirin, 4, 3)
				.setUnlocalizedName(MHFCReference.armor_kirin_boots_name));
		mhfcitemkirinShelm = registerItem(new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 0)
				.setUnlocalizedName(MHFCReference.armor_kirinS_helm_name));
		mhfcitemkirinSchest = registerItem(new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 1)
				.setUnlocalizedName(MHFCReference.armor_kirinS_chest_name));
		mhfcitemkirinSlegs = registerItem(new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 2)
				.setUnlocalizedName(MHFCReference.armor_kirinS_legs_name));
		mhfcitemkirinSboots = registerItem(new KirinSArmor(
				MHFCArmorMaterialHelper.ArmorKirinS, 4, 3)
				.setUnlocalizedName(MHFCReference.armor_kirinS_boots_name));
		mhfcitemyukumohelm = registerItem(new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 0)
				.setUnlocalizedName(MHFCReference.armor_yukumo_helm_name));
		mhfcitemyukumochest = registerItem(new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 1)
				.setUnlocalizedName(MHFCReference.armor_yukumo_chest_name));
		mhfcitemyukumolegs = registerItem(new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 2)
				.setUnlocalizedName(MHFCReference.armor_yukumo_legs_name));
		mhfcitemyukumoboots = registerItem(new YukumoArmor(
				MHFCArmorMaterialHelper.ArmorYukumo, 4, 3)
				.setUnlocalizedName(MHFCReference.armor_yukumo_boots_name));
		mhfcitemrathaloshelm = registerItem(new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 0)
				.setUnlocalizedName(MHFCReference.armor_rathalos_helm_name));
		mhfcitemrathaloschest = registerItem(new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 1)
				.setUnlocalizedName(MHFCReference.armor_rathalos_chest_name));
		mhfcitemrathaloslegs = registerItem(new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 2)
				.setUnlocalizedName(MHFCReference.armor_rathalos_legs_name));
		mhfcitemrathalosboots = registerItem(new RathalosArmor(
				MHFCArmorMaterialHelper.ArmorRathalos, 4, 3)
				.setUnlocalizedName(MHFCReference.armor_rathalos_boots_name));
		// Weapons
		MHFCItemGSTigrex = registerItem(new WeaponGSTigrex(
				MHFCWeaponMaterialHelper.GSTigrex));
		mhfcitemgsbone = registerItem(new WeaponGSBone(
				MHFCWeaponMaterialHelper.GSBoneBlade));
		mhfcitemgskirinthundersword = registerItem(new WeaponGSKirinThunderSword(
				MHFCWeaponMaterialHelper.GSKirin));
		MHFCItemHTigrex = registerItem(new WeaponHTigrex(
				MHFCWeaponMaterialHelper.HTigrex));
		mhfcitemlsironkatana = registerItem(new WeaponLSIronKatana(
				MHFCWeaponMaterialHelper.LSIronKatana));
		mhfcitemlsdarkvipern = registerItem(new WeaponLSDarkVipern(
				MHFCWeaponMaterialHelper.LSDarkVipern));
		mhfcitemhkirinspark = registerItem(new WeaponHKirinSpark(
				MHFCWeaponMaterialHelper.HKirinSpark));
		mhfcitemhwarhammer = registerItem(new WeaponHWar(
				MHFCWeaponMaterialHelper.HWarHammer));
		mhfcitemhwarhammerplus = registerItem(new WeaponHWarPlus(
				MHFCWeaponMaterialHelper.HWarHammerplus));
		mhfcitemhwarslammer = registerItem(new WeaponHWarSlammer(
				MHFCWeaponMaterialHelper.HWarSlammer));
		mhfcitemhhmetalbagpipe = registerItem(new WeaponHHMetalBagpipe(
				MHFCWeaponMaterialHelper.HHMetalBagpipe));
		// Range weapons
		mhfcitembhunter = registerItem(new WeaponBHunter());
		// Items... drops
		MHFCItemWyverniaDust = registerItem(new ItemWyverniaDust());
		MHFCItemFrontierSpawner = registerItem(new ItemFrontierSpawner());
		mhfcitemmoldediron = registerItem(new ItemMoldedIron());
		mhfcitemwoodrig = registerItem(new ItemWoodRig());
		mhfcitemlumberbar = registerItem(new ItemLumberBar());
		mhfcitemsteelbar = registerItem(new ItemSteelBar());
		MHFCItemTrapTool = registerItem(new ItemTrapTool());
		MHFCItemBombMaterial = registerItem(new ItemBombMaterial());
		MHFCItemGaguaEgg = registerItem(new ItemGaguaEgg());
		MHFCItemWyvernCoin = registerItem(new ItemWyvernCoin());
		mhfcitemingotcarbalite = registerItem(new ItemIngot(0));
		mhfcitemingotdragonite = registerItem(new ItemIngot(1));
		mhfcitemingoteltalite = registerItem(new ItemIngot(2));
		mhfcitemingotmachalite = registerItem(new ItemIngot(3));
		mhfcitemanunium = registerItem(new ItemBase(0));
		mhfcitemmeganum = registerItem(new ItemBase(1));
		mhfcitemkirinmane = registerItem(new ItemKirin(0));
		mhfcitemkiringem = registerItem(new ItemKirin(1));
		mhfcitemkirinthundertail = registerItem(new ItemKirin(2));
		mhfcitemlightcrystal = registerItem(new ItemKirin(3));
		mhfcitempurecrystal = registerItem(new ItemKirin(4));
		mhfcitemplatinummane = registerItem(new ItemKirin(5));
		MHFCItemTigrexScale = registerItem(new ItemTigrex(0));
		MHFCItemTigrexShell = registerItem(new ItemTigrex(1));
		mhfcitemtigrexclaw = registerItem(new ItemTigrex(2));
		mhfcitemtigrexfang = registerItem(new ItemTigrex(3));
		mhfcitemtigrexskullshell = registerItem(new ItemTigrex(4));
		mhfcitemtigrextail = registerItem(new ItemTigrex(5));
		mhfcitemflamesac = registerItem(new ItemSac(0));
		mhfcitemrathalosshell = registerItem(new ItemRathalos(0));
		mhfcitemrathaloswebbing = registerItem(new ItemRathalos(1));
		mhfcitemwyvernmarrow = registerItem(new ItemRathalos(2));
		mhfcitemrathaloswing = registerItem(new ItemRathalos(3));
		mhfcitemrathalosplate = registerItem(new ItemRathalos(4));
		mhfcitemfirestone = registerItem(new ItemFirestone());
		mhfcitemarmorsphere = registerItem(new ItemArmorSphere(0));
		mhfcitemarmorsphereplus = registerItem(new ItemArmorSphere(1));
		mhfcitemwyverniaclay = registerItem(new ItemWyverniaClay());
		// Foods
		mhfcitemkirinbuff = registerItem(new ItemKirinBuff(6, 100, false));
	}

	public static void init() {}

	private static Item registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());
		return item;
	}

}
