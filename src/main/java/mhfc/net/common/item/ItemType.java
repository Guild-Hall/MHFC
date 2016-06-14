package mhfc.net.common.item;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mhfc.net.common.weapon.melee.greatsword.ItemGreatsword;
import mhfc.net.common.weapon.melee.hammer.ItemHammer;
import mhfc.net.common.weapon.melee.huntinghorn.ItemHuntingHorn;
import mhfc.net.common.weapon.melee.longsword.ItemLongsword;
import mhfc.net.common.weapon.range.bow.ItemBow;
import mhfc.net.common.weapon.range.bowgun.heavy.ItemHeavyBowgun;
import mhfc.net.common.weapon.range.bowgun.light.ItemLightBowgun;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public enum ItemType {
	ARMOR_HEAD(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor) && ((ItemArmor) item).armorType == 0;
		}

		@Override
		public String getNameString() {
			return "type.armor_head.name";
		}
	},
	ARMOR_BODY(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor) && ((ItemArmor) item).armorType == 1;
		}

		@Override
		public String getNameString() {
			return "type.armor_body.name";
		}
	},
	ARMOR_PANTS(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor) && ((ItemArmor) item).armorType == 2;
		}

		@Override
		public String getNameString() {
			return "type.armor_pants.name";
		}
	},
	ARMOR_BOOTS(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor) && ((ItemArmor) item).armorType == 3;
		}

		@Override
		public String getNameString() {
			return "type.armor_boots.name";
		}
	},
	WEAPON_GREAT_SWORD(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemGreatsword);
		}

		@Override
		public String getNameString() {
			return "type.weapon_great_sword.name";
		}
	},
	WEAPON_LONG_SWORD(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemLongsword);
		}

		@Override
		public String getNameString() {
			return "type.weapon_long_sword.name";
		}
	},
	WEAPON_HAMMER(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemHammer);
		}

		@Override
		public String getNameString() {
			return "type.weapon_hammer.name";
		}
	},
	WEAPON_HUNTING_HORN(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemHuntingHorn);
		}

		@Override
		public String getNameString() {
			return "type.weapon_hunting_horn.name";
		}
	},
	WEAPON_SWORD_AND_SHIELD(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNameString() {
			return "type.weapon_sword_shield.name";
		}
	},
	WEAPON_DOUBLE_SWORD(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNameString() {
			return "type.weapon_double_sword.name";
		}
	},
	WEAPON_LANCE(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNameString() {
			return "type.weapon_lance.name";
		}
	},
	WEAPON_GUNLANCE(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNameString() {
			return "type.weapon_gunlance.name";
		}
	},
	WEAPON_BOW(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemBow);
		}

		@Override
		public String getNameString() {
			return "type.weapon_bow.name";
		}
	},
	WEAPON_SMALL_BOWGUN(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemLightBowgun);
		}

		@Override
		public String getNameString() {
			return "type.weapon_small_bowgun.name";
		}
	},
	WEAPON_BIG_BOWGUN(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemHeavyBowgun);
		}

		@Override
		public String getNameString() {
			return "type.weapon_big_bowgun.name";
		}
	},
	NO_OTHER(GeneralType.NONE) {
		@Override
		public boolean isTypeOf(Item item) {
			return false;
		}

		@Override
		public String getNameString() {
			return "type.no_item.name";
		}
	};

	public static enum GeneralType {
		ARMOR,
		WEAPON,
		NONE;
	}

	public final static List<ItemType> weaponTypes;

	public final static List<ItemType> armorTypes;

	public final static List<ItemType> allTypes = ImmutableList.copyOf(ItemType.values());

	static {
		List<ItemType> weapons = new ArrayList<>();
		List<ItemType> armors = new ArrayList<>();
		for (ItemType type : allTypes) {
			switch (type.generalType) {
			case ARMOR:
				armors.add(type);
				break;
			case WEAPON:
				weapons.add(type);
			default:
				break;
			}
		}
		weaponTypes = ImmutableList.copyOf(weapons);
		armorTypes = ImmutableList.copyOf(armors);
	}

	public static ItemType getTypeOf(ItemStack stack) {
		if (stack == null) {
			return NO_OTHER;
		}
		Item item = stack.getItem();
		return getTypeOf(item);

	}

	public static ItemType getTypeOf(Item item) {
		for (ItemType type : allTypes) {
			if (type.isTypeOf(item)) {
				return type;
			}
		}
		return NO_OTHER;
	}

	private final GeneralType generalType;

	private ItemType(GeneralType genType) {
		this.generalType = genType;
	}

	public GeneralType getGeneralType() {
		return generalType;
	}

	/**
	 * Returns the ordinal of the enum in the subgroup of weapon types. If the enum is not a weapon type, -1 is returned
	 *
	 */
	public int getWeaponOrdinal() {
		if (generalType == GeneralType.WEAPON) {
			return ordinal() - WEAPON_BIG_BOWGUN.ordinal();
		}
		return -1;
	}

	/**
	 * Returns the ordinal of the enum in the subgroup of armor types. If the enum is not an armor type, -1 is returned
	 *
	 */
	public int getArmorOrdinal() {
		if (generalType == GeneralType.ARMOR) {
			return ordinal() - ARMOR_BODY.ordinal();
		}
		return -1;
	}

	public abstract boolean isTypeOf(Item item);

	public abstract String getNameString();
}
