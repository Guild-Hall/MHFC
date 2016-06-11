package mhfc.net.common.item;

import mhfc.net.common.util.lib.MHFCReference;

public enum ItemRecolorableType {
	I_BONE(0, "item_bone", MHFCReference.tokenized_path_monster),
	I_CARAPACE(1, "item_carapace", MHFCReference.tokenized_path_monster),
	I_GEM(2, "item_gem", MHFCReference.tokenized_path_monster),
	I_MANTLE(3, "item_mantle", MHFCReference.tokenized_path_monster),
	I_ORE(4, "item_ore", MHFCReference.tokenized_path_monster),
	I_PART(5, "item_part", MHFCReference.tokenized_path_monster),
	I_PELT(6, "item_pelt", MHFCReference.tokenized_path_monster),
	I_POTION(7, "item_potion", MHFCReference.tokenized_path_monster),
	I_SAC(8, "item_sac", MHFCReference.tokenized_path_monster),
	I_SCALE(9, "item_scale", MHFCReference.tokenized_path_monster),
	I_SHARP(10, "item_sharp", MHFCReference.tokenized_path_monster),
	I_WEBBING(11, "item_webbing", MHFCReference.tokenized_path_monster),

	G_HELMET(12, "gear_head", MHFCReference.tokenized_path_gear),
	G_BODY(13, "gear_body", MHFCReference.tokenized_path_gear),
	G_FAULDS(14, "gear_fauld", MHFCReference.tokenized_path_gear),
	G_LEGS(15, "gear_leg", MHFCReference.tokenized_path_gear),
	G_EMPTY_PHIAL(16, "gear_phial_empty", MHFCReference.tokenized_path_gear),
	G_PHIAL(17, "gear_phial_full", MHFCReference.tokenized_path_gear),
	G_BULLET(18, "gear_shell", MHFCReference.tokenized_path_gear),
	G_KNIFE(19, "gear_knife", MHFCReference.tokenized_path_gear),

	W_BOW(20, "weapon_bow", MHFCReference.tokenized_path_weapons),
	W_CHARGEBLADE(21, "weapon_cb", MHFCReference.tokenized_path_weapons),
	W_DUALBLADE(22, "weapon_db", MHFCReference.tokenized_path_weapons),
	W_GUNLANCE(23, "weapon_gl", MHFCReference.tokenized_path_weapons),
	W_GREATSWORD(24, "weapon_gs", MHFCReference.tokenized_path_weapons),
	W_HAMMER(25, "weapon_hammer", MHFCReference.tokenized_path_weapons),
	W_HEAVYBG(26, "weapon_hbg", MHFCReference.tokenized_path_weapons),
	W_HORN(27, "weapon_hh", MHFCReference.tokenized_path_weapons),
	W_IGLAIVE(28, "weapon_ig", MHFCReference.tokenized_path_weapons),
	W_LANCE(29, "weapon_lance", MHFCReference.tokenized_path_weapons),
	W_LIGHTBG(30, "weapon_lbg", MHFCReference.tokenized_path_weapons),
	W_LONGSWORD(31, "weapon_ls", MHFCReference.tokenized_path_weapons),
	//So tempted to label it SWAGAXE...
	W_SWITCHAXE(32, "weapon_sa", MHFCReference.tokenized_path_weapons),
	W_SWORD(33, "weapon_sns", MHFCReference.tokenized_path_weapons),

	M_ARMORGEM(34, "misc_armorgem", MHFCReference.tokenized_path_misc),
	M_ARMORSPHERE(35, "misc_armorsphere", MHFCReference.tokenized_path_misc),
	M_BAIT(36, "misc_bait", MHFCReference.tokenized_path_misc),
	M_BERRY(37, "misc_berry", MHFCReference.tokenized_path_misc),
	M_BUG(38, "misc_bug", MHFCReference.tokenized_path_misc),
	M_CERT(39, "misc_cert", MHFCReference.tokenized_path_misc),
	M_DUNG(40, "misc_dung", MHFCReference.tokenized_path_misc),
	M_FISH(41, "misc_fish", MHFCReference.tokenized_path_misc),
	M_HUSK(42, "misc_husk", MHFCReference.tokenized_path_misc),
	M_MEAT(43, "misc_meat", MHFCReference.tokenized_path_misc),
	M_MYSTERY(44, "misc_mystery", MHFCReference.tokenized_path_misc),
	M_MUSHROOM(45, "misc_shroom", MHFCReference.tokenized_path_misc),
	M_SMOKE(46, "misc_smoke", MHFCReference.tokenized_path_misc),
	M_TOKEN(47, "misc_token", MHFCReference.tokenized_path_misc),
	M_VINE(48, "misc_vine", MHFCReference.tokenized_path_misc),

	T_BARREL(49, "tool_barrel", MHFCReference.tokenized_path_tools),
	T_BOOK(50, "tool_book", MHFCReference.tokenized_path_tools),
	T_BOOMERANG(51, "tool_boomerang", MHFCReference.tokenized_path_tools),
	T_HORN(52, "tool_horn", MHFCReference.tokenized_path_tools),
	T_KIT(53, "tool_kit", MHFCReference.tokenized_path_tools),
	T_MAP(54, "tool_map", MHFCReference.tokenized_path_tools),
	T_NET(55, "tool_net", MHFCReference.tokenized_path_tools),
	T_PICK(56, "tool_pick", MHFCReference.tokenized_path_tools),
	T_TRAP(57, "tool_trap", MHFCReference.tokenized_path_tools),
	T_WHETSTONE(58, "tool_whetstone", MHFCReference.tokenized_path_tools);

	public static final int COUNT = ItemRecolorableType.values().length;

	//Note: MUST MATCH FILE NAME.
	private final String unlocalizedName;
	private final String directory;
	public final int INDEX;
	private ItemRecolorableType(int index, String unlocalizedName, String directory) {
		this.unlocalizedName = unlocalizedName;
		this.directory = directory;
		this.INDEX = index;
	}
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	public String getQualifiedName() {
		return String.format(directory, unlocalizedName);
	}

	public static final ItemRecolorableType byIndex(int index) {
		for(ItemRecolorableType type : ItemRecolorableType.values()) {
			if(type.INDEX == index) {
				return type;
			}
		}

		return ItemRecolorableType.M_MYSTERY;
	}
}
