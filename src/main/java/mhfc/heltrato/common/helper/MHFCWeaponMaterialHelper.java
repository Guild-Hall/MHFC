package mhfc.heltrato.common.helper;

import mhfc.heltrato.common.item.weapon.greatsword.WeaponGSKirinThunderSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MHFCWeaponMaterialHelper {
	
	//GreatSword
	public static ToolMaterial GSBoneBlade = EnumHelper.addToolMaterial("BoneBlade", 3, 1000, 1.0F, 12, 5);
	public static ToolMaterial GSTigrex = EnumHelper.addToolMaterial("GSTigrex", 3, 2100, 1.0F, 41, 25);
	public static ToolMaterial GSKirin = EnumHelper.addToolMaterial("kirin", 3, 1750, 1.0F, 46f, 25); 
	public static ToolMaterial GSRathalos = EnumHelper.addToolMaterial("rath", 3, 4000, 1, 56, 25);
	public static ToolMaterial GSDeviljho = EnumHelper.addToolMaterial("gsdjho", 3, 5000, 1000, 81, 24);
	
	
	//LongSword
	public static ToolMaterial LSIronKatana = EnumHelper.addToolMaterial("IronKatana", 7, 1500, 3.0F, 17, 25);
	public static ToolMaterial LSDarkVipern = EnumHelper.addToolMaterial("LSDarkVipern", 3, 2000, 1, 39, 30);
	
	//Hammer
	public static ToolMaterial HWarHammer = EnumHelper.addToolMaterial("warH", 4, 1000, 1, 11f, 10);
	public static ToolMaterial HWarHammerplus = EnumHelper.addToolMaterial("Hwar+", 4, 1200, 1, 16, 15);
	public static ToolMaterial HWarSlammer = EnumHelper.addToolMaterial("warS", 4, 1350, 1, 22, 18);
	public static ToolMaterial HTigrex = EnumHelper.addToolMaterial("Tigrex Hammer", 3, 2500, 1.0F, 44, 5);
	public static ToolMaterial HKirinSpark = EnumHelper.addToolMaterial("Spark", 4, 2500, 1.0F, 63, 10);
	public static ToolMaterial HDeviljho = EnumHelper.addToolMaterial("Djho", 3, 5000, 2, 86, 25);
	
	
	//HuntingHorn
	public static ToolMaterial HHMetalBagpipe = EnumHelper.addToolMaterial("metalbagpipe", 4, 1000, 1f, 8, 4);
	
	public void init(){}

}
