package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public enum Note {
	White(0xFFFFFF, 1.f),
	Red(0xFF0000, 2 / 3f),
	Green(0x00FF00, 5 / 4f),
	Yellow(0xFFFF00, 3 / 2f),
	Cyan(0xAAAAFF, 2.f),
	Blue(0x5555FF, 1 / 2f * 4 / 5f),
	Purple(0xFF00FF, 1 / 2f),
	Orange(0xFFFF55, 4 / 5f);

	public final int colorRGB;
	private final String sound;
	private final float pitch;

	private Note(int color, float pitch) {
		this.colorRGB = color;
		this.sound = MHFCReference.weapon_hh_notesound;
		this.pitch = pitch;
	}

	public void playSound(ItemStack stack, EntityPlayer player) {
		player.playSound(sound, 1, pitch);
	}
}
