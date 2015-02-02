package mhfc.net.common.helper;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MHFCArmorMaterialHelper {
	
	
	// Basic Armors
	public static ArmorMaterial ArmorYukumo = EnumHelper.addArmorMaterial("yukumo", 120, new int[]{4, 9, 7, 4}, 25);
	public static ArmorMaterial ArmorRathalos = EnumHelper.addArmorMaterial("rathalos", 200, new int[]{5, 11, 6, 4}, 35);
	public static ArmorMaterial ArmorTigrex = EnumHelper.addArmorMaterial("tigrex", 300, new int[]{9, 17 , 13, 6}, 45);
	public static ArmorMaterial ArmorKirin = EnumHelper.addArmorMaterial("kirin", 325, new int[]{12, 18 , 14, 7}, 45);
	public static ArmorMaterial ArmorVelociprey = EnumHelper.addArmorMaterial("velociprey", 200, new int[]{3,7,5,2},45);
	
	// Senti Rank Armos
	public static ArmorMaterial ArmorDragoon = EnumHelper.addArmorMaterial("dragoon", 850, new int[]{14, 16, 15, 11}, 80);
	
	
	
	// S Rank Armors
	public static ArmorMaterial ArmorKirinS = EnumHelper.addArmorMaterial("kirinS", 700, new int[]{19, 35, 27, 16}, 70);
	public static ArmorMaterial ArmorTigrexB = EnumHelper.addArmorMaterial("kishin", 1250, new int[]{28, 41, 30, 24}, 150);
	
	

}
