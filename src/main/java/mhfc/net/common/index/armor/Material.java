package mhfc.net.common.index.armor;

import mhfc.net.common.index.ResourceInterface;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class Material {

	public static int enchant = 50;

	//@formatter:off
	// Basic Armors
	
	/**
	 * 
	 *  Will no more longer use for ISpecialArmor will take over soon @Heltrato
	 * */
	public static final ArmorMaterial initialMaterial = EnumHelper
			.addArmorMaterial("initialMaterial", ResourceInterface.armor_yukumo_tex, 1500, new int[] { 0, 0, 0, 0 }, enchant, null, 1500);
	public static final ArmorMaterial yukomo = EnumHelper
			.addArmorMaterial("yukumo", ResourceInterface.armor_yukumo_tex, 1500, new int[] { 0, 0, 0, 0  }, enchant, null, 1500);
	public static final ArmorMaterial velociprey = EnumHelper
			.addArmorMaterial("velociprey", ResourceInterface.armor_velociprey_tex, 1500, new int[] { 0, 0, 0, 0 }, enchant, null, 1500);
	public static final ArmorMaterial greatjaggi = EnumHelper
			.addArmorMaterial("greatjaggi", ResourceInterface.armor_greatjaggi_tex, 1500, new int[] {0, 0, 0, 0 }, enchant, null, 1500);
	public static final ArmorMaterial nibelsnarf = EnumHelper
			.addArmorMaterial("nibelsnarf", ResourceInterface.armor_nibelsnarf_tex, 1500, new int[] { 0, 0, 0, 0 }, enchant, null, 1500);
	public static final ArmorMaterial rathalos = EnumHelper
			.addArmorMaterial("rathalos", ResourceInterface.armor_rathalos_tex, 1500, new int[] {0, 0, 0, 0 }, enchant, null, 1500);
	public static final ArmorMaterial barroth = EnumHelper
			.addArmorMaterial("barroth", ResourceInterface.armor_barroth_tex, 1500, new int[] { 0, 0, 0, 0 }, enchant, null, 1500);
	public static final ArmorMaterial tigrex = EnumHelper
			.addArmorMaterial("tigrex", ResourceInterface.armor_tigrex_tex, 1500, new int[] { 0, 0, 0, 0 }, enchant, null, 1500);
	public static final ArmorMaterial kirin = EnumHelper
			.addArmorMaterial("kirin", ResourceInterface.armor_kirin_tex, 1500, new int[] { 0, 0, 0, 0  }, enchant, null, 1500);
	//public static final ArmorMaterial ArmorLagiacrus = EnumHelper
	//		.addArmorMaterial("lagia", "mhfc:lagiacrus", durA, new int[] { 4, 9, 5, 4 }, enchant, null, durA);
	//public static final ArmorMaterial ArmorNargacuga = EnumHelper
	//		.addArmorMaterial("nargacuga", "mhfc:narga", durA, new int[] { 4, 7, 4, 3 }, enchant, null, durA);
	public static final ArmorMaterial deviljho = EnumHelper
			.addArmorMaterial("deviljho", ResourceInterface.armor_deviljho_tex, 1500, new int[] { 0, 0, 0, 0}, enchant, null, 1500);

	// Donors Rank Armors
	public static final ArmorMaterial dragoon = EnumHelper
			.addArmorMaterial("dragoon", ResourceInterface.armor_dragoon_tex, 1500, new int[] { 4, 7, 4, 2 }, enchant, null, 1500);

	// S Rank Armors
	public static final ArmorMaterial kirinS = EnumHelper
			.addArmorMaterial("kirinS", ResourceInterface.armor_kirinS_tex, 1500, new int[] { 3, 13, 4, 3 }, enchant, null, 1500);
	public static final ArmorMaterial kishin = EnumHelper
			.addArmorMaterial("kishin", ResourceInterface.armor_tigrex_plus_tex, 1500, new int[] { 3, 11, 5, 4 }, enchant, null, 1500);

	//Community
	public static final ArmorMaterial bionic = EnumHelper
			.addArmorMaterial("bionic", ResourceInterface.armor_bionic_tex, 1500, new int[] { 4, 7, 4, 2 }, enchant, null, 1500);
}
