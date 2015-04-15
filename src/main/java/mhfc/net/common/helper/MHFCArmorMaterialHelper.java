package mhfc.net.common.helper;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MHFCArmorMaterialHelper {
	
	
	
	public static int enchant = 50;
	// Armor Durability: Basic = 400<durA> | Donors Rank = 850<durB> | S Rank = 1500<durC>
	//TODO G Rank , Promo, Exclusive<Choosen Players>
	public static int durA = 400, durB = 900, durC = 1500;

	// Basic Armors
	public static final ArmorMaterial ArmorYukumo = EnumHelper.addArmorMaterial         ("yukumo", durA, new int[]  {3, 5, 5, 3}, enchant);
	public static final ArmorMaterial ArmorVelociprey = EnumHelper.addArmorMaterial     ("velociprey",durA,new int[]{3, 6, 5, 2}, enchant);
	public static final ArmorMaterial ArmorRathalos = EnumHelper.addArmorMaterial       ("rathalos", durA, new int[]{3, 9, 5, 3}, enchant);
	public static final ArmorMaterial ArmorTigrex = EnumHelper.addArmorMaterial         ("tigrex", durA, new int[]  {3, 9, 7, 5}, enchant);
	public static final ArmorMaterial ArmorKirin = EnumHelper.addArmorMaterial          ("kirin", durA, new int[]   {3, 11, 6, 5},enchant);
	public static final ArmorMaterial ArmorLagiacrus = EnumHelper.addArmorMaterial      ("lagia", durA, new int[]   {3, 10, 5, 3},enchant);
	public static final ArmorMaterial ArmorBarroth = EnumHelper.addArmorMaterial        ("barroth", durA, new int[] {3, 15, 4, 3}, enchant);
	public static final ArmorMaterial ArmorCephalos = EnumHelper.addArmorMaterial       ("cepha", durA, new int[]   {3, 6, 5, 5}, enchant);

	// Donors Rank Armors
	public static final ArmorMaterial ArmorDragoon = EnumHelper.addArmorMaterial        ("dragoon", durB, new int[]{4, 7, 4, 2}, enchant);

	// S Rank Armors
	public static final ArmorMaterial ArmorKirinS = EnumHelper.addArmorMaterial         ("kirinS", durC, new int[]{5, 22, 9, 7}, enchant);
	public static final ArmorMaterial ArmorTigrexB = EnumHelper.addArmorMaterial        ("kishin", durC, new int[]{5, 28, 13, 3}, enchant);

}
