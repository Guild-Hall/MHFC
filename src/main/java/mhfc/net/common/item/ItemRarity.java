package mhfc.net.common.item;

import com.google.common.base.Preconditions;

public enum ItemRarity {
	R01(ItemColor.WHITE),
	R02(ItemColor.PURPLE),
	R03(ItemColor.YELLOW),
	R04(ItemColor.PINK),
	R05(ItemColor.LIME),
	R06(ItemColor.BLUE),
	R07(ItemColor.RED),
	R08(ItemColor.CYAN),
	R09(ItemColor.ORANGE),
	R10(ItemColor.MAGNTA);

	private static final ItemRarity[] allValues = values();

	private final int value;
	private final ItemColor defaultColor;

	private ItemRarity(ItemColor defaultColor) {
		this.value = ordinal() + 1;
		this.defaultColor = defaultColor;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	public static ItemRarity fromInt(int rarity) {
		Preconditions.checkArgument(rarity >= 1 && rarity <= 10, "rarity must be between 1 and 10");
		return allValues[rarity - 1];
	}
}
