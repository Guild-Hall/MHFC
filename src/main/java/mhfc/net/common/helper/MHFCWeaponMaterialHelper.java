package mhfc.net.common.helper;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MHFCWeaponMaterialHelper {
	
	
	/**
	 * The Weapon Material Helper Guide:
	 * Damage: All Weapon has a damage analysis base on the Weapon Tree.
	 * Durability Tree.
	 * Damage <= 20: 1000 Uses
	 * Damage <= 50 > 20: 1500 Uses.
	 * Damage <= 100 >50: 3000 Uses.
	 * Damage < 100: 5000 Uses.
	 * 
	 * */

	// GreatSword
	public static final ToolMaterial GSBoneBlade = EnumHelper.addToolMaterial         ("BoneBlade", 3, 1000, 1.0F, 14, 5);
	public static final ToolMaterial GSTigrex = EnumHelper.addToolMaterial            ("GSTigrex" , 3, 1500, 1.0F, 47, 25);
	public static final ToolMaterial GSKirin = EnumHelper.addToolMaterial             ("kirin"    , 3, 3000, 1.0F, 52, 25);
	public static final ToolMaterial GSRathalos = EnumHelper.addToolMaterial          ("rath"     , 3, 3000, 1.0F, 64, 25);
	public static final ToolMaterial GSDeviljho = EnumHelper.addToolMaterial          ("gsdjho"   , 3, 5000, 1.0F, 102, 24);
	public static final ToolMaterial GSDeadlySerpentBlade = EnumHelper.addToolMaterial("deadly"   , 3, 3000, 1.0f, 41, 24);

	/**
	 * =
	 * 
	 * Longsword Upgrade Tree
	 * @IronKatanaTree: "IK" + 12 Damage , 1st 5 Upgrade , LAST: Iron Katana' Gospel
	 * <SwitchUpgrade> [Eager Cleaver, Saber , Mirage Finsword]
	 * @EagerCleaverTree <Thunder Element>: |Starting = 46| Upgrade[1st 5 Upgrades] |+ 13 Base| +11 Thunder
	 * @SaberTree <Fire Element>: |Starting = 56| Upgrade[1st 5 Upgrades] + 17 Base | +7 Fire|
	 * @MirageFinswordTree <No Element>: |Starting = 77| Upgrade[1st 5 Upgrades] +26 Base |
	 * */
	  
	 
	public static final ToolMaterial LSIronKatana = EnumHelper.addToolMaterial         ("IronKatana"  , 3, 1000, 1.0f, 15, 25);
	public static final ToolMaterial LSIKGrace  = EnumHelper.addToolMaterial           ("IKGrace"     , 3, 1500, 1.0f, 27, 25);
	public static final ToolMaterial LSIKGospel = EnumHelper.addToolMaterial           ("IKGospel"    , 3, 1500, 1.0f, 39, 25);
	public static final ToolMaterial LSDarkVipern = EnumHelper.addToolMaterial         ("LSDarkVipern", 3, 1500, 1.0f, 39, 30);
	// Saber Tree
	public static final ToolMaterial LSSaber = EnumHelper.addToolMaterial              ("LSSaber"     , 3, 3000, 1.0f, 56, 30);
	public static final ToolMaterial LSLionDanceSaber = EnumHelper.addToolMaterial     ("LSSaber1"    , 3, 3000, 1.0f, 73, 30);
	public static final ToolMaterial LSLionKingSaber = EnumHelper.addToolMaterial      ("LSSaber2"    , 3, 3000, 1.0f, 90, 30);
	public static final ToolMaterial LSLionKaiserSaber = EnumHelper.addToolMaterial     ("LSSaber3"    , 3, 5000, 1.0f, 107, 30);
	// Eager Cleaver Tree
	public static final ToolMaterial LSEagerCleaver = EnumHelper.addToolMaterial       ("eagercleaver", 3, 2500, 1.0f, 46, 25);
	public static final ToolMaterial LSDevilSlicer = EnumHelper.addToolMaterial        ("devilslicer" , 3, 3000, 1.0f, 59, 30);
	public static final ToolMaterial LSTrueDevilSlicer = EnumHelper.addToolMaterial    ("tdevilslicer", 3, 3000, 1.0f, 72, 30);
	// Hammer
	public static final ToolMaterial HWarHammer = EnumHelper.addToolMaterial           ("warH"        , 4, 1000, 1.0f, 11, 10);
	public static final ToolMaterial HWarHammerplus = EnumHelper.addToolMaterial       ("Hwar+"       , 4, 1000, 1.0f, 16, 15);
	public static final ToolMaterial HWarSlammer = EnumHelper.addToolMaterial          ("warS"        , 4, 1500, 1.0f, 22, 18);
	public static final ToolMaterial HTigrex = EnumHelper.addToolMaterial              ("TigrexHammer", 3, 1500, 1.0f, 44, 5);
	public static final ToolMaterial HKirinSpark = EnumHelper.addToolMaterial          ("Spark"       , 4, 3000, 1.0f, 63, 10);
	public static final ToolMaterial HDeviljho = EnumHelper.addToolMaterial            ("Djho"        , 3, 3000, 1.0f, 86, 25);
	public static final ToolMaterial HRathalos = EnumHelper.addToolMaterial            ("rath"        , 4, 3000, 1.0f, 60, 40);
	// HuntingHorn
	public static final ToolMaterial HHMetalBagpipe = EnumHelper.addToolMaterial       ("metalbagpipe", 4, 1000, 1.0f, 8, 4);
	public static final ToolMaterial HHIvoryHorn = EnumHelper.addToolMaterial          ("ivoryhorn"   , 4, 1000, 1.0f, 5, 3);
	public static final ToolMaterial HHTigrex = EnumHelper.addToolMaterial             ("tigy"        , 4, 1500, 1.0f, 32, 25);

	public void init() {}

}
