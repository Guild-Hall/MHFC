package mhfc.net.common.helper;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ArmorMaterialHelper {

	public static int enchant = 50;
	// Armor Durability: Basic = 400<durA> | Donors Rank = 850<durB> | S Rank = 1500<durC>
	//TODO G Rank , Promo, Exclusive<Choosen Players>
	public static int durA = 400, durB = 900, durC = 1500;

	//@formatter:off
	// Basic Armors
	public static final ArmorMaterial ArmorYukumo = EnumHelper
			.addArmorMaterial("yukumo", MHFCReference.armor_yukumo_tex, durA, new int[] { 4, 6, 5, 3 }, enchant, null, durA);
	public static final ArmorMaterial ArmorVelociprey = EnumHelper
			.addArmorMaterial("velociprey", MHFCReference.armor_velociprey_tex, durA, new int[] { 4, 5, 4, 3 }, enchant, null, durA);
	public static final ArmorMaterial ArmorGreatJaggi = EnumHelper
			.addArmorMaterial("greatjaggi", MHFCReference.armor_greatjaggi_tex, durA, new int[] { 4, 5, 5, 3 }, enchant, null, durA);
	public static final ArmorMaterial ArmorNibelsnarf = EnumHelper
			.addArmorMaterial("nibelsnarf", MHFCReference.armor_nibelsnarf_tex, durA, new int[] { 4, 5, 5, 4 }, enchant, null, durA);
	public static final ArmorMaterial ArmorRathalos = EnumHelper
			.addArmorMaterial("rathalos", MHFCReference.armor_rathalos_tex, durA, new int[] { 4, 8, 6, 4 }, enchant, null, durA);
	public static final ArmorMaterial ArmorBarroth = EnumHelper
			.addArmorMaterial("barroth", MHFCReference.armor_barroth_tex, durA, new int[] { 4, 8, 6, 5 }, enchant, null, durA);
	public static final ArmorMaterial ArmorTigrex = EnumHelper
			.addArmorMaterial("tigrex", MHFCReference.armor_tigrex_tex, durA, new int[] { 4, 8, 8, 3 }, enchant, null, durA);
	public static final ArmorMaterial ArmorKirin = EnumHelper
			.addArmorMaterial("kirin", MHFCReference.armor_kirin_tex, durA, new int[] { 4, 8, 7, 4 }, enchant, null, durA);
	//public static final ArmorMaterial ArmorLagiacrus = EnumHelper
	//		.addArmorMaterial("lagia", "mhfc:lagiacrus", durA, new int[] { 4, 9, 5, 4 }, enchant, null, durA);
	//public static final ArmorMaterial ArmorNargacuga = EnumHelper
	//		.addArmorMaterial("nargacuga", "mhfc:narga", durA, new int[] { 4, 7, 4, 3 }, enchant, null, durA);
	public static final ArmorMaterial ArmorDeviljho = EnumHelper
			.addArmorMaterial("deviljho", MHFCReference.armor_deviljho_tex, durB, new int[] { 5, 8, 7, 7 }, enchant, null, durA);

	// Donors Rank Armors
	public static final ArmorMaterial ArmorDragoon = EnumHelper
			.addArmorMaterial("dragoon", MHFCReference.armor_dragoon_tex, durB, new int[] { 4, 7, 4, 2 }, enchant, null, durA);

	// S Rank Armors
	public static final ArmorMaterial ArmorKirinS = EnumHelper
			.addArmorMaterial("kirinS", MHFCReference.armor_kirinS_tex, durC, new int[] { 3, 13, 4, 3 }, enchant, null, durA);
	public static final ArmorMaterial ArmorTigrexB = EnumHelper
			.addArmorMaterial("kishin", MHFCReference.armor_tigrex_plus_tex, durC, new int[] { 3, 11, 5, 4 }, enchant, null, durA);

	//Community
	public static final ArmorMaterial ArmorST_1_Bionic = EnumHelper
			.addArmorMaterial("bionic", MHFCReference.armor_bionic_tex, durB, new int[] { 4, 7, 4, 2 }, enchant, null, durA);
}
