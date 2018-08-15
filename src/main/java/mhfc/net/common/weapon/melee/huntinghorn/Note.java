package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.Optional;
import java.util.function.Supplier;

import mhfc.net.common.core.registry.MHFCSoundRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

public enum Note {
	White(0xFFFFFF, 1.f),
	Red(0xFF0000, 2 / 3f),
	Green(0x00FF00, 5 / 4f),
	Yellow(0xFFFF00, 3 / 2f),
	Blue(0x5555FF, 1 / 2f * 4 / 5f),
	Purple(0xFF00FF, 1 / 2f),

	Cyan(0xAAAAFF, 2.f),
	Orange(0xFFFF55, 4 / 5f),
	Black(0x303030, 2 / 9F);

	public final int colorRGB;
	private final Supplier<Optional<SoundEvent>> sound;
	private final float pitch;

	private Note(int color, float pitch) {
		this.colorRGB = color;
		this.sound = MHFCSoundRegistry.serviceAccess.withIndirection(reg -> reg.huntingHornPlayNote);
		this.pitch = pitch;
	}

	public void playSound(ItemStack stack, EntityPlayer player) {
		player.playSound(sound.get().orElse(null), 1, pitch);
	}
}
