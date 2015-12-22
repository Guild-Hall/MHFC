package mhfc.net.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import mhfc.net.common.item.armor.BarrothArmor;
import mhfc.net.common.item.armor.DeviljhoArmor;
import mhfc.net.common.item.armor.DragoonArmor;
import mhfc.net.common.item.armor.GreatJaggiArmor;
import mhfc.net.common.item.armor.KirinArmor;
import mhfc.net.common.item.armor.KirinSArmor;
import mhfc.net.common.item.armor.KishinArmor;
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
import mhfc.net.common.item.materials.ItemWyverniaClay;
import mhfc.net.common.item.materials.ItemWyverniaDust;
import mhfc.net.common.weapon.melee.greatsword.GSBone;
import mhfc.net.common.weapon.melee.greatsword.GSDeadlySerpentBlade;
import mhfc.net.common.weapon.melee.greatsword.GSDeviljhobroadsword;
import mhfc.net.common.weapon.melee.greatsword.GSRathalosFiresword;
import mhfc.net.common.weapon.melee.greatsword.GSThunderSword;
import mhfc.net.common.weapon.melee.greatsword.GSTigrex;
import mhfc.net.common.weapon.melee.hammer.HDeviljho;
import mhfc.net.common.weapon.melee.hammer.HKirinSpark;
import mhfc.net.common.weapon.melee.hammer.HRathalos;
import mhfc.net.common.weapon.melee.hammer.HTigrex;
import mhfc.net.common.weapon.melee.hammer.HWar;
import mhfc.net.common.weapon.melee.hammer.HWarPlus;
import mhfc.net.common.weapon.melee.hammer.HWarSlammer;
import mhfc.net.common.weapon.melee.huntinghorn.HHIvoryHorn;
import mhfc.net.common.weapon.melee.huntinghorn.HHMetalBagpipe;
import mhfc.net.common.weapon.melee.huntinghorn.HHTigrex;
import mhfc.net.common.weapon.melee.longsword.LSDarkVipern;
import mhfc.net.common.weapon.melee.longsword.LSDevilSlicer;
import mhfc.net.common.weapon.melee.longsword.LSEagerCleaver;
import mhfc.net.common.weapon.melee.longsword.LSIKGospel;
import mhfc.net.common.weapon.melee.longsword.LSIKGrace;
import mhfc.net.common.weapon.melee.longsword.LSIronKatana;
import mhfc.net.common.weapon.melee.longsword.LSLionDanceSaber;
import mhfc.net.common.weapon.melee.longsword.LSLionKaiserSaber;
import mhfc.net.common.weapon.melee.longsword.LSLionKingSaber;
import mhfc.net.common.weapon.melee.longsword.LSLionsRoarSaber;
import mhfc.net.common.weapon.melee.longsword.LSMirageFinsword;
import mhfc.net.common.weapon.melee.longsword.LSMirageFinswordplus;
import mhfc.net.common.weapon.melee.longsword.LSPhantomMirage;
import mhfc.net.common.weapon.melee.longsword.LSSaber;
import mhfc.net.common.weapon.melee.longsword.LSTrueDevilSlicer;
import mhfc.net.common.weapon.range.bow.BHunters;
import mhfc.net.common.weapon.range.bowgun.heavy.BHRath;
import mhfc.net.common.weapon.range.bowgun.light.BLBarrel;
import net.minecraft.item.Item;

public class MHFCItemRegistry {
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

	public static final Item weapon_bgl_barrel;
	public static final Item weapon_bgh_rath;

	public static final Item weapon_bow_hunters;

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

	public static final Item armor_barroth_helm;
	public static final Item armor_barroth_chest;
	public static final Item armor_barroth_legs;
	public static final Item armor_barroth_boots;

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

	public static final Item armor_deviljho_helm;
	public static final Item armor_deviljho_chest;
	public static final Item armor_deviljho_legs;
	public static final Item armor_deviljho_boots;

	public static final Item armor_jaggi_helm;
	public static final Item armor_jaggi_chest;
	public static final Item armor_jaggi_legs;
	public static final Item armor_jaggi_boots;

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

		/**
		 * @author Heltrato: "Please sort the weapon by there Rarity.. Thanks"
		 * 
		 */

		// Armor
		armor_yukumo_helm = registerItem(new YukumoArmor(0));
		armor_velociprey_helm = registerItem(new VelocipreyArmor(0));
		armor_barroth_helm = registerItem(new BarrothArmor(0));
		armor_jaggi_helm = registerItem(new GreatJaggiArmor(0));
		armor_rathalos_helm = registerItem(new RathalosArmor(0));
		armor_tigrex_helm = registerItem(new TigrexArmor(0));
		armor_kirin_helm = registerItem(new KirinArmor(0));
		armor_deviljho_helm = registerItem(new DeviljhoArmor(0));
		armor_dragoon_helm = registerItem(new DragoonArmor(0));
		armor_kirinS_helm = registerItem(new KirinSArmor(0));
		armor_tigrexB_helm = registerItem(new KishinArmor(0));
		armor_bionic_helm = registerItem(new ST_Bionic(0));

		armor_yukumo_chest = registerItem(new YukumoArmor(1));
		armor_velociprey_chest = registerItem(new VelocipreyArmor(1));
		armor_barroth_chest = registerItem(new BarrothArmor(1));
		armor_jaggi_chest = registerItem(new GreatJaggiArmor(1));
		armor_rathalos_chest = registerItem(new RathalosArmor(1));
		armor_tigrex_chest = registerItem(new TigrexArmor(1));
		armor_kirin_chest = registerItem(new KirinArmor(1));
		armor_deviljho_chest = registerItem(new DeviljhoArmor(1));
		armor_dragoon_chest = registerItem(new DragoonArmor(1));
		armor_kirinS_chest = registerItem(new KirinSArmor(1));
		armor_tigrexB_chest = registerItem(new KishinArmor(1));
		armor_bionic_chest = registerItem(new ST_Bionic(1));

		armor_yukumo_legs = registerItem(new YukumoArmor(2));
		armor_velociprey_legs = registerItem(new VelocipreyArmor(2));
		armor_barroth_legs = registerItem(new BarrothArmor(2));
		armor_jaggi_legs = registerItem(new GreatJaggiArmor(2));
		armor_rathalos_legs = registerItem(new RathalosArmor(2));
		armor_tigrex_legs = registerItem(new TigrexArmor(2));
		armor_kirin_legs = registerItem(new KirinArmor(2));
		armor_deviljho_legs = registerItem(new DeviljhoArmor(2));
		armor_dragoon_legs = registerItem(new DragoonArmor(2));
		armor_kirinS_legs = registerItem(new KirinSArmor(2));
		armor_tigrexB_legs = registerItem(new KishinArmor(2));
		armor_bionic_legs = registerItem(new ST_Bionic(2));

		armor_yukumo_boots = registerItem(new YukumoArmor(3));
		armor_velociprey_boots = registerItem(new VelocipreyArmor(3));
		armor_barroth_boots = registerItem(new BarrothArmor(3));
		armor_jaggi_boots = registerItem(new GreatJaggiArmor(3));
		armor_rathalos_boots = registerItem(new RathalosArmor(3));
		armor_tigrex_boots = registerItem(new TigrexArmor(3));
		armor_kirin_boots = registerItem(new KirinArmor(3));
		armor_deviljho_boots = registerItem(new DeviljhoArmor(3));
		armor_dragoon_boots = registerItem(new DragoonArmor(3));
		armor_kirinS_boots = registerItem(new KirinSArmor(3));
		armor_tigrexB_boots = registerItem(new KishinArmor(3));
		armor_bionic_boots = registerItem(new ST_Bionic(3));

		// Weapons
		weapon_gs_bone = registerItem(new GSBone());
		weapon_gs_deadlyserpentblade = registerItem(new GSDeadlySerpentBlade());
		weapon_gs_tigrex = registerItem(new GSTigrex());
		weapon_gs_kirinthunders = registerItem(new GSThunderSword());
		weapon_gs_berserkers = registerItem(new GSDeviljhobroadsword());
		weapon_gs_rathalosfire = registerItem(new GSRathalosFiresword());

		weapon_ls_ironkatana = registerItem(new LSIronKatana());
		weapon_ls_ironkatanagrace = registerItem(new LSIKGrace());
		weapon_ls_ironkatanagospel = registerItem(new LSIKGospel());
		weapon_ls_eagercleaver = registerItem(new LSEagerCleaver());
		weapon_ls_devilslicer = registerItem(new LSDevilSlicer());
		weapon_ls_truedevilslicer = registerItem(new LSTrueDevilSlicer());
		weapon_ls_saber = registerItem(new LSSaber());
		weapon_ls_liondancesaber = registerItem(new LSLionDanceSaber());
		weapon_ls_lionkingsaber = registerItem(new LSLionKingSaber());
		weapon_ls_lionkaisersaber = registerItem(new LSLionKaiserSaber());
		weapon_ls_miragefinsword = registerItem(new LSMirageFinsword());
		weapon_ls_miragefinswordplus = registerItem(new LSMirageFinswordplus());
		weapon_ls_phantommirage = registerItem(new LSPhantomMirage());
		weapon_ls_darkvipern = registerItem(new LSDarkVipern());
		weapon_ls_lionsroarsaber = registerItem(new LSLionsRoarSaber());

		weapon_hm_warhammer = registerItem(new HWar());
		weapon_hm_warhammerplus = registerItem(new HWarPlus());
		weapon_hm_warslammer = registerItem(new HWarSlammer());
		weapon_hm_tigrex = registerItem(new HTigrex());
		weapon_hm_devilsdue = registerItem(new HDeviljho());
		weapon_hm_rathalos = registerItem(new HRathalos());
		weapon_hm_kirinspark = registerItem(new HKirinSpark());

		weapon_hh_ivoryhorn = registerItem(new HHIvoryHorn());
		weapon_hh_metalbagpipe = registerItem(new HHMetalBagpipe());
		weapon_hh_tigrex = registerItem(new HHTigrex());

		// Range weapons

		weapon_bow_hunters = registerItem(new BHunters());

		weapon_bgl_barrel = registerItem(new BLBarrel());
		weapon_bgh_rath = registerItem(new BHRath());
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

	private static Item registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());
		return item;
	}

}
