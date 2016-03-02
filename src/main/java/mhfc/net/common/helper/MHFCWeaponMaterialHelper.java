package mhfc.net.common.helper;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MHFCWeaponMaterialHelper {
	
	public static int durA = 1000, durB = 1500, durC = 3000, durD = 5000, enchA = 30;
	public static float defaulteffiency = 1.0F;
	
	/**
	 * The Weapon Material Helper Guide:
	 * Damage: All Weapon has a damage analysis base on the Weapon Tree.
	 * Durability Tree.
	 * Damage <= 20: 1000 Uses       ;<durA>
	 * Damage <= 50 > 20: 1500 Uses. ;<durB>
	 * Damage <= 100 >50: 3000 Uses. ;<durC>
	 * Damage < 100: 5000 Uses.		 ;<durD>	
	 * 
	 * */

	// GreatSword
	public static final ToolMaterial GSBoneBlade = EnumHelper.addToolMaterial         ("BoneBlade", 3, durA, defaulteffiency, 14, enchA);
	public static final ToolMaterial GSTigrex = EnumHelper.addToolMaterial            ("GSTigrex" , 3, durB, defaulteffiency, 42, enchA);
	public static final ToolMaterial GSKirin = EnumHelper.addToolMaterial             ("kirin"    , 3, durC, defaulteffiency, 46, enchA);
	public static final ToolMaterial GSRathalos = EnumHelper.addToolMaterial          ("rath"     , 3, durC, defaulteffiency, 51, enchA);
	public static final ToolMaterial GSDeviljho = EnumHelper.addToolMaterial          ("gsdjho"   , 3, 5000, defaulteffiency, 70, enchA);
	public static final ToolMaterial GSDeadlySerpentBlade = EnumHelper.addToolMaterial("deadly"   , 3, durC, defaulteffiency, 35, enchA);

	/**
	 * 
	 * 
	 * Longsword Upgrade Tree
	 * @BoneTree: + 9 Damage Default last BoneKatanaHunt
	 * @IronKatanaTree: "IK" + 12 Damage , 1st 5 Upgrade , LAST: Iron Katana' Gospel
	 * <SwitchUpgrade> [Eager Cleaver, Saber , Mirage Finsword]
	 * @EagerCleaverTree <Thunder Element>: |Starting = 46| Upgrade[1st 5 Upgrades] |+ 13 Base| +11 Thunder
	 * @SaberTree <Fire Element>: |Starting = 56| Upgrade[1st 5 Upgrades] + 17 Base | +7 Fire|
	 * @MirageFinswordTree <No Element>: |Starting = 77| Upgrade[1st 5 Upgrades] +26 Base |
	 * */
	// Bone Tree
	public static final ToolMaterial LSBone = EnumHelper.addToolMaterial               ("bone1"       , 3, durA, defaulteffiency, 8, enchA);
	public static final ToolMaterial LSWhiteBone = EnumHelper.addToolMaterial          ("bone2"       , 3, durB, defaulteffiency, 10, enchA);
	public static final ToolMaterial LSBoneHunt = EnumHelper.addToolMaterial           ("bone3"       , 3, durB, defaulteffiency, 13, enchA);
	
	// LSBone 3 new branch wolf, shark dragon, <titan>:special branch.
	// Bone Katana Wolf <No Element> +11 Damage per upgrade
	public static final ToolMaterial LSBoneWolf = EnumHelper.addToolMaterial           ("bone4"       , 3, durB, defaulteffiency, 15, enchA);
	// Bone Katana Shark <Water Element [Soon]> +9 Damage per upgrade
	public static final ToolMaterial LSBoneShark = EnumHelper.addToolMaterial          ("bone5"       , 3, durB, defaulteffiency, 17, enchA);
	// Bone Katana Dragon <Dragon Element [Soon]> +21 Damage; R
	public static final ToolMaterial LSBoneDragon = EnumHelper.addToolMaterial         ("bone6"       , 3, durC, defaulteffiency, 19, enchA);
	
	
	// Iron Katana Tree 
	public static final ToolMaterial LSIronKatana = EnumHelper.addToolMaterial         ("ik1"         , 3, durA, defaulteffiency, 18, enchA);
	public static final ToolMaterial LSIKGrace  = EnumHelper.addToolMaterial           ("ik2"         , 3, durB, defaulteffiency, 23, enchA);
	public static final ToolMaterial LSIKGospel = EnumHelper.addToolMaterial           ("ik3"         , 3, durB, defaulteffiency, 26, enchA);
	
	// Saber Tree
	public static final ToolMaterial LSSaber = EnumHelper.addToolMaterial              ("LSSaber"     , 3, durC, defaulteffiency, 31, enchA);
	public static final ToolMaterial LSLionDanceSaber = EnumHelper.addToolMaterial     ("LSSaber1"    , 3, durC, defaulteffiency, 34, enchA);
	public static final ToolMaterial LSLionKingSaber = EnumHelper.addToolMaterial      ("LSSaber2"    , 3, durC, defaulteffiency, 39, enchA);
	public static final ToolMaterial LSLionKaiserSaber = EnumHelper.addToolMaterial    ("LSSaber3"    , 3, durD, defaulteffiency, 47, enchA);
	
	// Eager Cleaver Tree
	public static final ToolMaterial LSEagerCleaver = EnumHelper.addToolMaterial       ("eagercleaver", 3, durC, defaulteffiency, 32, enchA);
	public static final ToolMaterial LSDevilSlicer = EnumHelper.addToolMaterial        ("devilslicer" , 3, durC, defaulteffiency, 37, enchA);
	public static final ToolMaterial LSTrueDevilSlicer = EnumHelper.addToolMaterial    ("tdevilslicer", 3, durC, defaulteffiency, 49, enchA);
	// Mirage Finsword Tree
	public static final ToolMaterial LSMirageFinsword = EnumHelper.addToolMaterial     ("lsmirage1"   , 3, durC, defaulteffiency, 33, enchA);
	public static final ToolMaterial LSMirageFinswordplus = EnumHelper.addToolMaterial ("lsmirage2"   , 3, durD, defaulteffiency, 41, enchA);
	public static final ToolMaterial LSPhantomMirage = EnumHelper.addToolMaterial      ("lsmirage3"   , 3, durD, defaulteffiency, 50, enchA);
	
	// Dark Vipern Tree
	public static final ToolMaterial LSDarkVipern = EnumHelper.addToolMaterial         ("LSDarkVipern", 3, durB, defaulteffiency, 16, enchA);
	
	// Lions Roar Saber Tree [Original Mod Tree] +26 per upgrade.
	public static final ToolMaterial LSLionsRoarSaber = EnumHelper.addToolMaterial     ("LSSaber4"    , 3, durD, defaulteffiency, 59, enchA);
	
	
	/**
	 * Hammer
	 * */
	
	public static final ToolMaterial HWarHammer = EnumHelper.addToolMaterial           ("warH1"       , 4, durA, defaulteffiency, 11, enchA);
	public static final ToolMaterial HWarHammerplus = EnumHelper.addToolMaterial       ("warH2"       , 4, durA, defaulteffiency, 16, enchA);
	public static final ToolMaterial HWarSlammer = EnumHelper.addToolMaterial          ("warH3"       , 4, durB, defaulteffiency, 22, enchA);
	public static final ToolMaterial HTigrex = EnumHelper.addToolMaterial              ("TigrexHammer", 3, durB, defaulteffiency, 44, enchA);
	public static final ToolMaterial HKirinSpark = EnumHelper.addToolMaterial          ("Spark"       , 4, durC, defaulteffiency, 63, enchA);
	public static final ToolMaterial HDeviljho = EnumHelper.addToolMaterial            ("Djho"        , 3, durC, defaulteffiency, 86, enchA);
	public static final ToolMaterial HRathalos = EnumHelper.addToolMaterial            ("rath"        , 4, durC, defaulteffiency, 60, enchA);
	
	
	
	/**
	 * 
	 * Hunting Horn
	 * */
	
	public static final ToolMaterial HHMetalBagpipe = EnumHelper.addToolMaterial       ("hha", 4, durA, defaulteffiency, 8, enchA);
	public static final ToolMaterial HHIvoryHorn = EnumHelper.addToolMaterial          ("hhb"   , 4, durA, defaulteffiency, 5, enchA);
	public static final ToolMaterial HHTigrex = EnumHelper.addToolMaterial             ("hhc"        , 4, durB, defaulteffiency, 32, enchA);
	public static final ToolMaterial HHGreatbagpipe = EnumHelper.addToolMaterial       ("hhd"        , 4, durB, defaulteffiency, 12, enchA);
	public static final ToolMaterial HHHeavyBagpipe = EnumHelper.addToolMaterial       ("hhe"        , 4, durB, defaulteffiency, 16, enchA);
	public static final ToolMaterial HHHeavyBagpipeplus = EnumHelper.addToolMaterial   ("hhf"        , 4, durB, defaulteffiency, 19, enchA);
	public static final ToolMaterial HHEliteBagpipe = EnumHelper.addToolMaterial       ("hhg"        , 4, durC, defaulteffiency, 25, enchA);
	public static final ToolMaterial HHWardrums = EnumHelper.addToolMaterial           ("hhh"        , 4, durB, defaulteffiency, 14, enchA);
	public static final ToolMaterial HHWardrumsplus = EnumHelper.addToolMaterial       ("hhi"        , 4, durB, defaulteffiency, 18, enchA);
	public static final ToolMaterial HHMogwardDrums = EnumHelper.addToolMaterial       ("hhj"        , 4, durB, defaulteffiency, 35, enchA);
	public static final ToolMaterial HHDarkThornTrumpet = EnumHelper.addToolMaterial   ("hhk"        , 4, durC, defaulteffiency, 71, enchA);
	public static final ToolMaterial HHBlackCasket = EnumHelper.addToolMaterial        ("hhl"        , 4, durC, defaulteffiency, 84, enchA);
	
	public void init() {}

}
