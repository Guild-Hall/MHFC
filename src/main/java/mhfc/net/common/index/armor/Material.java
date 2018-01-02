package mhfc.net.common.index.armor;

import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class Material {

	public static int enchant = 50;
	// Armor Durability: Basic = 400<durA> | Donors Rank = 850<durB> | S Rank = 1500<durC>
	//TODO G Rank , Promo, Exclusive<Choosen Players>
	public static int durA = 400, durB = 900, durC = 1500;

	//@formatter:off
	// Basic Armors
	
	/**
	 * 
	 *  Will no more longer use for ISpecialArmor will take over soon @Heltrato
	 * */
	public static final ArmorMaterial initialMaterial = EnumHelper
			.addArmorMaterial("initialMaterial", ResourceInterface.armor_yukumo_tex, 1500, new int[] { 0, 0, 0, 0 }, enchant, null, durA);
	public static final ArmorMaterial yukomo = EnumHelper
			.addArmorMaterial("yukumo", ResourceInterface.armor_yukumo_tex, durA, new int[] { 4, 6, 5, 3 }, enchant, null, durA);
	public static final ArmorMaterial velociprey = EnumHelper
			.addArmorMaterial("velociprey", ResourceInterface.armor_velociprey_tex, durA, new int[] { 4, 5, 4, 3 }, enchant, null, durA);
	public static final ArmorMaterial greatjaggi = EnumHelper
			.addArmorMaterial("greatjaggi", ResourceInterface.armor_greatjaggi_tex, durA, new int[] { 4, 5, 5, 3 }, enchant, null, durA);
	public static final ArmorMaterial nibelsnarf = EnumHelper
			.addArmorMaterial("nibelsnarf", ResourceInterface.armor_nibelsnarf_tex, durA, new int[] { 4, 5, 5, 4 }, enchant, null, durA);
	public static final ArmorMaterial rathalos = EnumHelper
			.addArmorMaterial("rathalos", ResourceInterface.armor_rathalos_tex, durA, new int[] { 4, 8, 6, 4 }, enchant, null, durA);
	public static final ArmorMaterial barroth = EnumHelper
			.addArmorMaterial("barroth", ResourceInterface.armor_barroth_tex, durA, new int[] { 0, 0, 0, 0 }, enchant, null, durA);
	public static final ArmorMaterial tigrex = EnumHelper
			.addArmorMaterial("tigrex", ResourceInterface.armor_tigrex_tex, durA, new int[] { 4, 8, 8, 3 }, enchant, null, durA);
	public static final ArmorMaterial kirin = EnumHelper
			.addArmorMaterial("kirin", ResourceInterface.armor_kirin_tex, durA, new int[] { 4, 8, 7, 4 }, enchant, null, durA);
	//public static final ArmorMaterial ArmorLagiacrus = EnumHelper
	//		.addArmorMaterial("lagia", "mhfc:lagiacrus", durA, new int[] { 4, 9, 5, 4 }, enchant, null, durA);
	//public static final ArmorMaterial ArmorNargacuga = EnumHelper
	//		.addArmorMaterial("nargacuga", "mhfc:narga", durA, new int[] { 4, 7, 4, 3 }, enchant, null, durA);
	public static final ArmorMaterial deviljho = EnumHelper
			.addArmorMaterial("deviljho", ResourceInterface.armor_deviljho_tex, durB, new int[] { 5, 8, 7, 7 }, enchant, null, durA);

	// Donors Rank Armors
	public static final ArmorMaterial dragoon = EnumHelper
			.addArmorMaterial("dragoon", ResourceInterface.armor_dragoon_tex, durB, new int[] { 4, 7, 4, 2 }, enchant, null, durA);

	// S Rank Armors
	public static final ArmorMaterial kirinS = EnumHelper
			.addArmorMaterial("kirinS", ResourceInterface.armor_kirinS_tex, durC, new int[] { 3, 13, 4, 3 }, enchant, null, durA);
	public static final ArmorMaterial kishin = EnumHelper
			.addArmorMaterial("kishin", ResourceInterface.armor_tigrex_plus_tex, durC, new int[] { 3, 11, 5, 4 }, enchant, null, durA);

	//Community
	public static final ArmorMaterial bionic = EnumHelper
			.addArmorMaterial("bionic", ResourceInterface.armor_bionic_tex, durB, new int[] { 4, 7, 4, 2 }, enchant, null, durA);
}
