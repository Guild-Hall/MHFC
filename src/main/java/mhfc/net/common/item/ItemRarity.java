package mhfc.net.common.item;

import mhfc.net.common.item.ItemColor;

public enum ItemRarity {
	R00( 0, ItemColor.GRAY),
	R01( 1, ItemColor.WHITE),
	R02( 2, ItemColor.PURPLE),
	R03( 3, ItemColor.YELLOW),
	R04( 4, ItemColor.PINK),
	R05( 5, ItemColor.LIME),
	R06( 6, ItemColor.BLUE),
	R07( 7, ItemColor.RED),
	R08( 8, ItemColor.CYAN),
	R09( 9, ItemColor.ORANGE),
	R10(10, ItemColor.MAGNTA);
	private final int value;
	private final ItemColor defaultColor;
	private ItemRarity(int value, ItemColor defaultColor) {
		this.value = value;
		this.defaultColor = defaultColor;
	}
}
