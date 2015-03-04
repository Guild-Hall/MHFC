package mhfc.net.common.item;

import mhfc.net.common.weapon.melee.greatsword.GreatswordClass;
import mhfc.net.common.weapon.melee.hammer.HammerClass;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornClass;
import mhfc.net.common.weapon.melee.longsword.LongswordClass;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public enum ItemType {
	ARMOR_HEAD(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor)
					&& ((ItemArmor) item).armorType == 0;
		}

		@Override
		public String getNameString() {
			return "type.armor_head.name";
		}
	},
	ARMOR_BODY(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor)
					&& ((ItemArmor) item).armorType == 1;
		}

		@Override
		public String getNameString() {
			return "type.armor_body.name";
		}
	},
	ARMOR_PANTS(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor)
					&& ((ItemArmor) item).armorType == 2;
		}

		@Override
		public String getNameString() {
			return "type.armor_pants.name";
		}
	},
	ARMOR_BOOTS(GeneralType.ARMOR) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof ItemArmor)
					&& ((ItemArmor) item).armorType == 3;
		}

		@Override
		public String getNameString() {
			return "type.armor_boots.name";
		}
	},
	WEAPON_GREAT_SWORD(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof GreatswordClass);
		}

		@Override
		public String getNameString() {
			return "type.weapon_great_sword.name";
		}
	},
	WEAPON_LONG_SWORD(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof LongswordClass);
		}

		@Override
		public String getNameString() {
			return "type.weapon_long_sword.name";
		}
	},
	WEAPON_HAMMER(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof HammerClass);
		}

		@Override
		public String getNameString() {
			return "type.weapon_hammer.name";
		}
	},
	WEAPON_HUNTING_HORN(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			return (item instanceof HuntingHornClass);
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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNameString() {
			return "type.weapon_bow.name";
		}
	},
	WEAPON_SMALL_BOWGUN(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNameString() {
			return "type.weapon_small_bowgun.name";
		}
	},
	WEAPON_BIG_BOWGUN(GeneralType.WEAPON) {
		@Override
		public boolean isTypeOf(Item item) {
			// TODO Auto-generated method stub
			return false;
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

	public final static ItemType[] weaponTypes = {WEAPON_GREAT_SWORD,
			WEAPON_LONG_SWORD, WEAPON_SWORD_AND_SHIELD, WEAPON_DOUBLE_SWORD,
			WEAPON_HAMMER, WEAPON_HUNTING_HORN, WEAPON_LANCE, WEAPON_GUNLANCE,
			WEAPON_BIG_BOWGUN, WEAPON_SMALL_BOWGUN, WEAPON_BOW};

	public final static ItemType[] armorTypes = {ARMOR_HEAD, ARMOR_BODY,
			ARMOR_PANTS, ARMOR_BOOTS};

	public static ItemType getTypeOf(ItemStack stack) {
		if (stack == null)
			return NO_OTHER;
		Item item = stack.getItem();
		return getTypeOf(item);

	}

	public static ItemType getTypeOf(Item item) {
		ItemType[] types = ItemType.values();
		for (int i = 0; i < types.length; i++) {
			if (types[i].isTypeOf(item))
				return types[i];
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
	 * Returns the ordinal of the enum in the subgroup of weapon types. If the
	 * enum is not a weapon type, -1 is returned
	 * 
	 */
	public int getWeaponOrdinal() {
		if (generalType == GeneralType.WEAPON)
			return ordinal() - 4;
		return -1;
	}

	/**
	 * Returns the ordinal of the enum in the subgroup of armor types. If the
	 * enum is not an armor type, -1 is returned
	 * 
	 */
	public int getArmorOrdinal() {
		if (generalType == GeneralType.ARMOR)
			return ordinal();
		return -1;
	}

	public abstract boolean isTypeOf(Item item);

	public abstract String getNameString();
}
