package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.common.configuration.MHFCConfig;
import mhfc.heltrato.common.helper.MHFCArmorMaterialHelper;
import mhfc.heltrato.common.helper.MHFCWeaponMaterialHelper;
import mhfc.heltrato.common.item.ItemArmorSphere;
import mhfc.heltrato.common.item.ItemBase;
import mhfc.heltrato.common.item.ItemBombMaterial;
import mhfc.heltrato.common.item.ItemBullet;
import mhfc.heltrato.common.item.ItemFirestone;
import mhfc.heltrato.common.item.ItemFrontierSpawner;
import mhfc.heltrato.common.item.ItemGaguaEgg;
import mhfc.heltrato.common.item.ItemIngot;
import mhfc.heltrato.common.item.ItemKirin;
import mhfc.heltrato.common.item.ItemLumberBar;
import mhfc.heltrato.common.item.ItemMoldedIron;
import mhfc.heltrato.common.item.ItemRathalos;
import mhfc.heltrato.common.item.ItemSac;
import mhfc.heltrato.common.item.ItemSteelBar;
import mhfc.heltrato.common.item.ItemTigrex;
import mhfc.heltrato.common.item.ItemTrapTool;
import mhfc.heltrato.common.item.ItemWoodRig;
import mhfc.heltrato.common.item.ItemWyvernCoin;
import mhfc.heltrato.common.item.ItemWyverniaClay;
import mhfc.heltrato.common.item.ItemWyverniaDust;
import mhfc.heltrato.common.item.armor.DragoonArmor;
import mhfc.heltrato.common.item.armor.KirinArmor;
import mhfc.heltrato.common.item.armor.KirinSArmor;
import mhfc.heltrato.common.item.armor.KishinArmor;
import mhfc.heltrato.common.item.armor.RathalosArmor;
import mhfc.heltrato.common.item.armor.TigrexArmor;
import mhfc.heltrato.common.item.armor.VelocipreyArmor;
import mhfc.heltrato.common.item.armor.YukumoArmor;
import mhfc.heltrato.common.item.food.ItemKirinBuff;
import mhfc.heltrato.common.item.food.ItemMeats;
import mhfc.heltrato.common.item.food.ItemNutrients;
import mhfc.heltrato.common.item.weapon.greatsword.WeaponGSBone;
import mhfc.heltrato.common.item.weapon.greatsword.WeaponGSDeviljhobroadsword;
import mhfc.heltrato.common.item.weapon.greatsword.WeaponGSKirinThunderSword;
import mhfc.heltrato.common.item.weapon.greatsword.WeaponGSRathalosFiresword;
import mhfc.heltrato.common.item.weapon.greatsword.WeaponGSTigrex;
import mhfc.heltrato.common.item.weapon.hammer.WeaponHDeviljho;
import mhfc.heltrato.common.item.weapon.hammer.WeaponHKirinSpark;
import mhfc.heltrato.common.item.weapon.hammer.WeaponHRathalos;
import mhfc.heltrato.common.item.weapon.hammer.WeaponHTigrex;
import mhfc.heltrato.common.item.weapon.hammer.WeaponHWar;
import mhfc.heltrato.common.item.weapon.hammer.WeaponHWarPlus;
import mhfc.heltrato.common.item.weapon.hammer.WeaponHWarSlammer;
import mhfc.heltrato.common.item.weapon.huntinghorn.WeaponHHIvoryHorn;
import mhfc.heltrato.common.item.weapon.huntinghorn.WeaponHHMetalBagpipe;
import mhfc.heltrato.common.item.weapon.huntinghorn.WeaponHHTigrex;
import mhfc.heltrato.common.item.weapon.longsword.WeaponLSDarkVipern;
import mhfc.heltrato.common.item.weapon.longsword.WeaponLSIronKatana;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegItem {
	
	private static MHFCConfig id;
	private static GameRegistry itemreg;
	private static MHFCWeaponMaterialHelper mat;
	
	//Weapons
	public static Item mhfcitemgsbone;
	public static Item MHFCItemGSTigrex;
	public static Item mhfcitemgskirinthundersword;
	public static Item mhfcitemgsberserkerssword;
	public static Item mhfcitemgsrathalosfiresword;
	public static Item MHFCItemHTigrex;
	public static Item mhfcitemhkirinspark;
	public static Item mhfcitemhwarhammer;
	public static Item mhfcitemhwarhammerplus;
	public static Item mhfcitemhwarslammer;
	public static Item mhfcitemhdevilsdue;
	public static Item mhfcitemhrathalos;
	public static Item mhfcitemlsironkatana;
	public static Item mhfcitembhunter;
	public static Item mhfcitemhhivoryhorn;
	public static Item mhfcitemhhmetalbagpipe;
	public static Item mhfcitemlsdarkvipern;
	public static Item mhfcitembglshooterbarrel;
	public static Item mhfcitembglspartacusfire;
	public static Item mhfcitembtigrexarrow;
	public static Item mhfcitembadventurer;
	public static Item mhfcitemhhtigrex;
	
	//Armors
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
	public static Item mhfcitemdragoonhelm;
	public static Item mhfcitemdragoonchest;
	public static Item mhfcitemdragoonlegs;
	public static Item mhfcitemdragoonboots;
	public static Item mhfcitemvelocipreyhelm;
	public static Item mhfcitemvelocipreychest;
	public static Item mhfcitemvelocipreylegs;
	public static Item mhfcitemvelocipreyboots;
	public static Item mhfcitemdeviljhohelm;
	public static Item mhfcitemdeviljhochest;
	public static Item mhfcitemdeviljholegs;
	public static Item mhfcitemdeviljhoboots;
	public static Item mhfcitemtigrexbhelm;
	public static Item mhfcitemtigrexbchest;
	public static Item mhfcitemtigrexblegs;
	public static Item mhfcitemtigrexbboots;
	
	//Stuffs
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
	public static Item mhfcfoodnutrients;
	
	public static Item mhfcitembullet0;
	public static Item mhfcitembullet1;
	public static Item mhfcitembullet2;
	public static Item mhfcitembullet3;
	
	//Foods
	public static Item mhfcfoodkirinbuff;
	public static Item mhfcfoodrawmeat;
	public static Item mhfcfoodcookmeat;
	public static Item mhfcfoodboostmeat;
	public static Item mhfcfoodprotectionmeat;
	public static Item mhfcfoodpoisonmeat;
	public static Item mhfcfoodslowmeat;
	public static Item mhfcfoodhungermeat;
	public static Item mhfcfoodfiremeat;
	public static Item mhfcfoodmeganutrients;
	
	public static void init(){
		registerArmor();
		registerMeleeWeapon();
		registerItem();
		registerRangeWeapon();
		registerFood();
		
		getItemID(mhfcitemlsironkatana);
		getItemID(mhfcitemlsdarkvipern);
		getItemID(mhfcitemgsbone);
		getItemID(MHFCItemGSTigrex);
		getItemID(mhfcitemgskirinthundersword);
		getItemID(mhfcitemgsberserkerssword);
		getItemID(mhfcitemgsrathalosfiresword);
		getItemID(mhfcitemhwarhammer);
		getItemID(mhfcitemhwarhammerplus);
		getItemID(mhfcitemhwarslammer);
		getItemID(MHFCItemHTigrex);
		getItemID(mhfcitemhkirinspark);
		getItemID(mhfcitemhrathalos);
		getItemID(mhfcitemhdevilsdue);
		getItemID(mhfcitemhhivoryhorn);
		getItemID(mhfcitemhhmetalbagpipe);
		getItemID(mhfcitemhhtigrex);
//		getItemID(mhfcitembadventurer);
//		getItemID(mhfcitembhunter);
//		getItemID(mhfcitembtigrexarrow);
//		getItemID(mhfcitembglshooterbarrel);
//		getItemID(mhfcitembglspartacusfire);
		
		//// Armors
		
		// Helm
		getItemID(mhfcitemyukumohelm);
		getItemID(mhfcitemvelocipreyhelm);
		getItemID(mhfcitemrathaloshelm);
		getItemID(mhfcitemtigrexhelm);
		getItemID(mhfcitemkirinhelm);
		getItemID(mhfcitemdragoonhelm);
		getItemID(mhfcitemkirinShelm);
		getItemID(mhfcitemtigrexbhelm);
		// Chest
		getItemID(mhfcitemyukumochest);
		getItemID(mhfcitemvelocipreychest);
		getItemID(mhfcitemrathaloschest);
		getItemID(mhfcitemtigrexchest);
		getItemID(mhfcitemkirinchest);
		getItemID(mhfcitemdragoonchest);
		getItemID(mhfcitemkirinSchest);
		getItemID(mhfcitemtigrexbchest);
		// Legs
		getItemID(mhfcitemyukumolegs);
		getItemID(mhfcitemvelocipreylegs);
		getItemID(mhfcitemrathaloslegs);
		getItemID(mhfcitemtigrexlegs);
		getItemID(mhfcitemkirinlegs);
		getItemID(mhfcitemdragoonlegs);
		getItemID(mhfcitemkirinSlegs);
		getItemID(mhfcitemtigrexblegs);
		// Boots
		getItemID(mhfcitemyukumoboots);
		getItemID(mhfcitemvelocipreyboots);
		getItemID(mhfcitemrathalosboots);
		getItemID(mhfcitemtigrexboots);
		getItemID(mhfcitemkirinboots);
		getItemID(mhfcitemdragoonboots);
		getItemID(mhfcitemkirinSboots);
		getItemID(mhfcitemtigrexbboots);
		
		//Stuffs
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
		getItemID(mhfcfoodkirinbuff);
		getItemID(mhfcitembullet0);
		getItemID(mhfcitembullet1);
		getItemID(mhfcitembullet2);
		getItemID(mhfcitembullet3);
		getItemID(mhfcfoodnutrients);
		getItemID(mhfcfoodmeganutrients);
		getItemID(mhfcfoodrawmeat);
		getItemID(mhfcfoodcookmeat);
		getItemID(mhfcfoodboostmeat);
		getItemID(mhfcfoodprotectionmeat);
		getItemID(mhfcfoodpoisonmeat);
		getItemID(mhfcfoodslowmeat);
		getItemID(mhfcfoodhungermeat);
		getItemID(mhfcfoodfiremeat);
		
		
	}
	
	private static void registerArmor()
	{
		mhfcitemtigrexhelm = new TigrexArmor(MHFCArmorMaterialHelper.ArmorTigrex, 4, 0).setUnlocalizedName("a.mhf_helm");
		mhfcitemkirinhelm = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin, 4, 0).setUnlocalizedName("b.mhf_helm");
		mhfcitemtigrexchest = new TigrexArmor(MHFCArmorMaterialHelper.ArmorTigrex, 4, 1).setUnlocalizedName("a.mhf_chest");
		mhfcitemkirinchest = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin, 4, 1).setUnlocalizedName("b.mhf_chest");
		mhfcitemtigrexlegs = new TigrexArmor(MHFCArmorMaterialHelper.ArmorTigrex, 4, 2).setUnlocalizedName("a.mhf_leg");
		mhfcitemkirinlegs = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin, 4, 2).setUnlocalizedName("b.mhf_legs");
		mhfcitemtigrexboots = new TigrexArmor(MHFCArmorMaterialHelper.ArmorTigrex, 4, 3).setUnlocalizedName("a.mhf_boots");
		mhfcitemkirinboots = new KirinArmor(MHFCArmorMaterialHelper.ArmorKirin, 4, 3).setUnlocalizedName("b.mhf_boots");
		mhfcitemkirinShelm = new KirinSArmor(MHFCArmorMaterialHelper.ArmorKirinS, 4, 0).setUnlocalizedName("c.mhf_helm");
		mhfcitemkirinSchest = new KirinSArmor(MHFCArmorMaterialHelper.ArmorKirinS, 4, 1).setUnlocalizedName("c.mhf_chest");
		mhfcitemkirinSlegs = new KirinSArmor(MHFCArmorMaterialHelper.ArmorKirinS, 4, 2).setUnlocalizedName("c.mhf_leg");
		mhfcitemkirinSboots = new KirinSArmor(MHFCArmorMaterialHelper.ArmorKirinS, 4, 3).setUnlocalizedName("c.mhf_boots");
		mhfcitemyukumohelm = new YukumoArmor(MHFCArmorMaterialHelper.ArmorYukumo, 4, 0).setUnlocalizedName("d.mhf_helm");
		mhfcitemyukumochest = new YukumoArmor(MHFCArmorMaterialHelper.ArmorYukumo, 4, 1).setUnlocalizedName("d.mhf_chest");
		mhfcitemyukumolegs = new YukumoArmor(MHFCArmorMaterialHelper.ArmorYukumo, 4, 2).setUnlocalizedName("d.mhf_leg");
		mhfcitemyukumoboots = new YukumoArmor(MHFCArmorMaterialHelper.ArmorYukumo, 4, 3).setUnlocalizedName("d.mhf_boots");
		mhfcitemrathaloshelm = new RathalosArmor(MHFCArmorMaterialHelper.ArmorRathalos, 4, 0).setUnlocalizedName("e.mhf_helm");
		mhfcitemrathaloschest = new RathalosArmor(MHFCArmorMaterialHelper.ArmorRathalos, 4, 1).setUnlocalizedName("e.mhf_chest");
		mhfcitemrathaloslegs = new RathalosArmor(MHFCArmorMaterialHelper.ArmorRathalos, 4, 2).setUnlocalizedName("e.mhf_leg");
		mhfcitemrathalosboots = new RathalosArmor(MHFCArmorMaterialHelper.ArmorRathalos, 4, 3).setUnlocalizedName("e.mhf_boots");
		mhfcitemdragoonhelm =new DragoonArmor(MHFCArmorMaterialHelper.ArmorDragoon, 4, 0).setUnlocalizedName("f.mhf_helm");
		mhfcitemdragoonchest =new DragoonArmor(MHFCArmorMaterialHelper.ArmorDragoon, 4, 1).setUnlocalizedName("f.mhf_chest");
		mhfcitemdragoonlegs =new DragoonArmor(MHFCArmorMaterialHelper.ArmorDragoon, 4, 2).setUnlocalizedName("f.mhf_leg");
		mhfcitemdragoonboots =new DragoonArmor(MHFCArmorMaterialHelper.ArmorDragoon, 4, 3).setUnlocalizedName("f.mhf_boots");
		mhfcitemvelocipreyhelm = new VelocipreyArmor(MHFCArmorMaterialHelper.ArmorVelociprey, 4, 0).setUnlocalizedName("g.mhf_helm");
		mhfcitemvelocipreychest = new VelocipreyArmor(MHFCArmorMaterialHelper.ArmorVelociprey, 4, 1).setUnlocalizedName("g.mhf_chest");
		mhfcitemvelocipreylegs = new VelocipreyArmor(MHFCArmorMaterialHelper.ArmorVelociprey, 4, 2).setUnlocalizedName("g.mhf_leg");
		mhfcitemvelocipreyboots = new VelocipreyArmor(MHFCArmorMaterialHelper.ArmorVelociprey, 4, 3).setUnlocalizedName("g.mhf_boots");
		mhfcitemtigrexbhelm = new KishinArmor(MHFCArmorMaterialHelper.ArmorTigrexB, 4, 0).setUnlocalizedName("h.mhf_helm");
		mhfcitemtigrexbchest = new KishinArmor(MHFCArmorMaterialHelper.ArmorTigrexB, 4, 1).setUnlocalizedName("h.mhf_chest");
		mhfcitemtigrexblegs = new KishinArmor(MHFCArmorMaterialHelper.ArmorTigrexB, 4, 2).setUnlocalizedName("h.mhf_leg");
		mhfcitemtigrexbboots = new KishinArmor(MHFCArmorMaterialHelper.ArmorTigrexB, 4, 3).setUnlocalizedName("h.mhf_boots");
	}
	
	private static void registerMeleeWeapon()
	{
		MHFCItemGSTigrex = new WeaponGSTigrex(mat.GSTigrex);
		mhfcitemgsbone = new WeaponGSBone(mat.GSBoneBlade);
		mhfcitemgskirinthundersword = new WeaponGSKirinThunderSword(mat.GSKirin);
		mhfcitemgsberserkerssword = new WeaponGSDeviljhobroadsword(mat.GSDeviljho);
		mhfcitemgsrathalosfiresword = new WeaponGSRathalosFiresword(mat.GSRathalos);
		MHFCItemHTigrex = new WeaponHTigrex(mat.HTigrex);
		mhfcitemlsironkatana = new WeaponLSIronKatana(mat.LSIronKatana);
		mhfcitemlsdarkvipern = new WeaponLSDarkVipern(mat.LSDarkVipern);
		mhfcitemhkirinspark = new WeaponHKirinSpark(mat.HKirinSpark);
		mhfcitemhdevilsdue = new WeaponHDeviljho(mat.HDeviljho);
		mhfcitemhwarhammer = new WeaponHWar(mat.HWarHammer);
		mhfcitemhwarhammerplus = new WeaponHWarPlus(mat.HWarHammerplus);
		mhfcitemhwarslammer = new WeaponHWarSlammer(mat.HWarSlammer);
		mhfcitemhhmetalbagpipe = new WeaponHHMetalBagpipe(mat.HHMetalBagpipe);	
		mhfcitemhhivoryhorn = new WeaponHHIvoryHorn(mat.HHIvoryHorn);
		mhfcitemhhtigrex = new WeaponHHTigrex(mat.HHTigrex);
		mhfcitemhrathalos = new WeaponHRathalos(mat.HRathalos);
		
		
	}
	
	private static void registerRangeWeapon()
	{
//		mhfcitembadventurer = new WeaponBAdventurer();
//		mhfcitembhunter = new WeaponBHunter();
//		mhfcitembtigrexarrow = new WeaponBTigrexArrow();
//		mhfcitembglshooterbarrel = new WeaponBGLShooterBarrel();
//		mhfcitembglspartacusfire = new WeaponBGLSpartacusFire();
	}
	private static void registerItem()
	{
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
        mhfcitembullet0 = new ItemBullet(0);
        mhfcitembullet1 = new ItemBullet(1);
        mhfcitembullet2 = new ItemBullet(2);
        mhfcitembullet3 = new ItemBullet(3);
		
				
	}
	
	private static void registerFood() {
		mhfcfoodkirinbuff = new ItemKirinBuff(6, 100, false);
		mhfcfoodrawmeat = new ItemMeats(2, 40, true, 0);
		mhfcfoodcookmeat = new ItemMeats(3, 100, true, 1);
		mhfcfoodboostmeat = new ItemMeats(3, 100, true, 2).setPotionEffect(Potion.jump.id, 250, 3 ,10);
		mhfcfoodprotectionmeat = new ItemMeats(4, 125, true, 3).setPotionEffect(Potion.resistance.id, 250 , 3 , 10);
		mhfcfoodpoisonmeat = new ItemMeats(3, 50, true, 4).setPotionEffect(Potion.poison.id, 180 , 2 , 10);
		mhfcfoodslowmeat = new ItemMeats(3, 100, true, 5).setPotionEffect(Potion.moveSlowdown.id, 400,2, 10);
		mhfcfoodhungermeat = new ItemMeats(2, 30, true, 6).setPotionEffect(Potion.hunger.id, 400 , 2, 10);
		mhfcfoodfiremeat = new ItemMeats(4, 150, true, 7).setPotionEffect(Potion.damageBoost.id, 600 , 2 ,10);
		mhfcfoodnutrients = new ItemNutrients(2, 50, true, 0);
		mhfcfoodmeganutrients = new ItemNutrients(3,70, true, 1);
	}
	
	private static void getItemID(Item item){
		itemreg.registerItem(item, item.getUnlocalizedName());
		
	}

}
