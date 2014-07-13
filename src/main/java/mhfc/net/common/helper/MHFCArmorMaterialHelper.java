package mhfc.net.common.helper;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MHFCArmorMaterialHelper {

	public static ArmorMaterial ArmorYukumo = EnumHelper.addArmorMaterial(
			"yukumo", 120, new int[]{6, 10, 8, 4}, 25);
	public static ArmorMaterial ArmorRathalos = EnumHelper.addArmorMaterial(
			"rathalos", 200, new int[]{5, 11, 6, 4}, 35);
	public static ArmorMaterial ArmorTigrex = EnumHelper.addArmorMaterial(
			"tigrex", 300, new int[]{11, 17, 13, 8}, 45);
	public static ArmorMaterial ArmorKirin = EnumHelper.addArmorMaterial(
			"kirin", 325, new int[]{12, 18, 14, 10}, 45);
	public static ArmorMaterial ArmorKirinS = EnumHelper.addArmorMaterial(
			"kirinS", 700, new int[]{24, 33, 27, 18}, 70);

	public void init() {}

}
