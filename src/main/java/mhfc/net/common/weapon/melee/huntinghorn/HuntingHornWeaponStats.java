package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import mhfc.net.common.util.NBTUtils;
import mhfc.net.common.weapon.melee.MeleeWeaponStats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HuntingHornWeaponStats extends MeleeWeaponStats {
	protected static final String NBT_NOTEHISTORY = "mhfc:lastNotes";
	private static Note[] allNotes = Note.values();

	public static class HuntingHornWeaponStatsBuilder extends MeleeWeaponStatsBuilder<HuntingHornWeaponStatsBuilder> {
		public HuntingHornWeaponStatsBuilder() {}

		@Override
		protected HuntingHornWeaponStatsBuilder getThis() {
			return this;
		}

		@Override
		public HuntingHornWeaponStats build() {
			return new HuntingHornWeaponStats(this);
		}
	}

	protected HuntingHornWeaponStats(HuntingHornWeaponStatsBuilder builder) {
		super(builder);
	}

	public void onNotePlayed(ItemStack stack, EntityPlayer player, Note note) {
		Objects.requireNonNull(note);
		int[] notes = NBTUtils.getNBTChecked(stack).getIntArray(NBT_NOTEHISTORY);
		if (notes.length >= HHSongRegistry.SONG_LENGTH_CAP) {
			System.arraycopy(notes, 1, notes, 0, HHSongRegistry.SONG_LENGTH_CAP - 1);
			notes[HHSongRegistry.SONG_LENGTH_CAP - 1] = note.ordinal();
		} else {
			notes = Arrays.copyOf(notes, notes.length + 1);
			notes[notes.length - 1] = note.ordinal();
			NBTUtils.getNBTChecked(stack).setIntArray(NBT_NOTEHISTORY, notes);
		}
		Note[] properNotes = new Note[notes.length];
		for (int i = 0; i < notes.length; i++) {
			properNotes[i] = allNotes[notes[i]];
		}
		List<Note> notesAsList = Arrays.asList(properNotes);
		HHSongRegistry.getSong(notesAsList).ifPresent(s -> s.onPlayed(player, stack, this));
	}

}
