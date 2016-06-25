package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public enum Note {
	White(0xFFFFFF, 1.f),
	Red(0xFF0000, 0.7f),
	Green(0x00FF00, 0.9f),
	Yellow(0xFFFF00, 1.3f),
	Cyan(0xAAAAFF, 1.5f),
	Blue(0x5555FF, 0.5f),
	Purple(0xFF00FF, 0.3f),
	Orange(0xFFFF55, 0.8f);

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
