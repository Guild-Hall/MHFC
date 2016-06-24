package mhfc.net.common.weapon.melee.huntinghorn;

public enum Note {
	White(0xFFFFFF),
	Red(0xFF0000),
	Green(0x00FF00),
	Yellow(0xFFFF00),
	Cyan(0xAAAAFF),
	Blue(0x5555FF),
	Purple(0xFF00FF),
	Orange(0xFFFF55);

	public final int colorRGBA;

	private Note(int color) {
		this.colorRGBA = color;
	}
}
