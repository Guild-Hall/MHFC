package mhfc.net.common.helper;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MHFCWeaponMaterialHelper {

	// GreatSword
	public static final ToolMaterial GSBoneBlade = EnumHelper.addToolMaterial(
			"BoneBlade", 3, 1000, 1.0F, 14, 5);
	public static final ToolMaterial GSTigrex = EnumHelper.addToolMaterial(
			"GSTigrex", 3, 2100, 1.0F, 47, 25);
	public static final ToolMaterial GSKirin = EnumHelper.addToolMaterial(
			"kirin", 3, 1750, 1.0F, 52f, 25);
	public static final ToolMaterial GSRathalos = EnumHelper.addToolMaterial(
			"rath", 3, 4000, 1, 64, 25);
	public static final ToolMaterial GSDeviljho = EnumHelper.addToolMaterial(
			"gsdjho", 3, 5000, 1000, 102, 24);
	public static final ToolMaterial GSDeadlySerpentBlade = EnumHelper.addToolMaterial(
			"deadly", 3, 5000, 100, 41, 24);

	// LongSword
	
	/**
	 * LS Tree Weapon Damage additional:
	 * 
	 * @Iron Katana Tree: "IK" + 12 Damage , 1st 5 Upgrade , + 16 Damage next 5upgrade..
	 * Switch Upgrade + 
	 * 
	 * 
	 * */
	  
	 
	public static final ToolMaterial LSIronKatana = EnumHelper.addToolMaterial(
			"IronKatana", 7, 1500, 3.0F, 15, 25);
	
	public static final ToolMaterial LSIKGrace  = EnumHelper.addToolMaterial(
			"IKGrace", 3, 1500, 1, 27, 25);
	
	public static final ToolMaterial LSIKGospel = EnumHelper.addToolMaterial
			("IKGospel", 3, 2500, 1, 39, 25);
	
	public static final ToolMaterial LSDarkVipern = EnumHelper.addToolMaterial(
			"LSDarkVipern", 3, 2000, 1, 39, 30);
	
	public static final ToolMaterial LSEagerCleaver = EnumHelper.addToolMaterial(
			"eagercleaver", 3, 2500, 1, 51, 25);

	// Hammer
	public static final ToolMaterial HWarHammer = EnumHelper.addToolMaterial(
			"warH", 4, 1000, 1, 11f, 10);
	public static final ToolMaterial HWarHammerplus = EnumHelper
			.addToolMaterial("Hwar+", 4, 1200, 1, 16, 15);
	public static final ToolMaterial HWarSlammer = EnumHelper.addToolMaterial(
			"warS", 4, 1350, 1, 22, 18);
	public static final ToolMaterial HTigrex = EnumHelper.addToolMaterial(
			"Tigrex Hammer", 3, 2500, 1.0F, 44, 5);
	public static final ToolMaterial HKirinSpark = EnumHelper.addToolMaterial(
			"Spark", 4, 2500, 1.0F, 63, 10);
	public static final ToolMaterial HDeviljho = EnumHelper.addToolMaterial(
			"Djho", 3, 5000, 2, 86, 25);
	public static final ToolMaterial HRathalos = EnumHelper.addToolMaterial(
			"rath", 4, 2000, 1, 60, 40);

	// HuntingHorn
	public static final ToolMaterial HHMetalBagpipe = EnumHelper
			.addToolMaterial("metalbagpipe", 4, 1000, 1f, 8, 4);
	public static final ToolMaterial HHIvoryHorn = EnumHelper.addToolMaterial(
			"ivoryhorn", 4, 650, 1, 5, 3);
	public static final ToolMaterial HHTigrex = EnumHelper.addToolMaterial(
			"tigy", 4, 1700, 1, 32, 25);

	public void init() {}

}
